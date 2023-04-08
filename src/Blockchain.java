import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;
    private List<Transaction> currentTransactions;
    private boolean useProofOfStake; // Added field for proof of stake
    private static  double minStakerequired = 1000; // Example: Minimum required stake to create a block is 1000
    private Map<User, Double> validators; // Map to store validator addresses and their stakes

    public Blockchain(int difficulty, boolean useProofOfStake) {
        this.chain = new ArrayList<Block>();
        this.difficulty = difficulty;
        this.currentTransactions = new ArrayList<Transaction>();
        this.chain.add(createGenesisBlock());
        this.useProofOfStake = useProofOfStake; // Initialize useProofOfStake field
        if (useProofOfStake) {
            validators = new HashMap<>();
        }
    }

    private Block createGenesisBlock() {
        return new Block(new ArrayList<Transaction>(),  new Date().getTime(),"0");
    }

    public void printBlockchain() {

        System.out.println("INICIO_Blockchain: " + this.toString());
        System.out.println("Blockchain:Difficulty: " + this.difficulty);
        System.out.println("Blockchain:Blocks_INICIO ");
        int count = 0;
        for(Block block : this.chain)
        {
            block.printBlock();
            count++;
        }
        System.out.println("Blockchain:Blocks_FIM: " + count + " BLOCKS");
        System.out.println("Blockchain:Transactions_INICIO ");
        count = 0;
        for(Transaction transaction : this.currentTransactions)
        {
            transaction.printTransaction();
            count++;
        }
        System.out.println("Blockchain:Transactions_FIM: " + count + " Transactions");

        System.out.println("FIM_Blockchain: " + this.toString());
    }


    public void addTransaction(Transaction transaction) {
        this.currentTransactions.add(transaction);
    }


/*
    public Block mineBlock(List<Transaction> pendingTransactions, User miner) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(pendingTransactions); //Adicionar ao current_transaction? PEDRO
        //transactions.add(Transaction.rewardTransaction(miner.getAddress()));
        Block block = new Block(transactions, 0, this.getLastBlock().getHash());
        block.mineBlock(this.difficulty);
        return block;
    }
*/
    // With Proof of Stake
    public Block mineBlock(List<Transaction> pendingTransactions, User miner) {

        if (useProofOfStake && this.validators.containsKey(miner) && miner.getBalance() >= minStakerequired) {
            List<Transaction> transactions = new ArrayList<>();
            transactions.addAll(pendingTransactions);
            // Add reward transaction for User
            transactions.add(Transaction.rewardTransaction(miner));
            miner.subtractBalance(1); // Decrease stake by 1 for successful block creation
            Block block = new Block(transactions, new Date().getTime(), this.getLastBlock().getHash());
            block.mineBlock(this.difficulty);
            return block;
        } else if (!useProofOfStake) {
            // If not using PoS, mine the block as usual
            List<Transaction> transactions = new ArrayList<>();
            transactions.addAll(pendingTransactions);
            transactions.add(Transaction.rewardTransaction(miner));
            Block block = new Block(transactions, new Date().getTime(), this.getLastBlock().getHash());
            block.mineBlock(this.difficulty);
            return block;
        }
        //else if validator does not meet PoS requirements, return null
        System.out.println("User does not meet PoS requirements!");
        return null;

      /*

        Block block = new Block(transactions, new Date().getTime(), this.getLastBlock().getHash());
        block.mineBlock(this.difficulty);
        return block; */
    }

    public boolean isValidBlock(String previousBlockHash, Block block) {
        String hash = block.calculateHash();
        String prefix = "0".repeat(this.difficulty);
        return block.getPreviousBlockHash().equals(previousBlockHash) && hash.substring(0, this.difficulty).equals(prefix);
    }

    public boolean isValidChain() {
        for (int i = 1; i < this.chain.size(); i++) {
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i - 1);

            if (!currentBlock.getPreviousBlockHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (!isValidBlock(previousBlock.getHash(), currentBlock)) {
                return false;
            }
        }

        return true;
    }

    public void replaceChain(List<Block> newChain) {
        if (isChainLengthValid(newChain) && isChainValid(newChain)) {
            this.chain = newChain;
            this.currentTransactions = new ArrayList<Transaction>();
        }
    }

    private boolean isChainLengthValid(List<Block> chain) {
        return chain.size() > this.chain.size();
    }

    public boolean isChainValid(List<Block> chain) {
        Block previousBlock = chain.get(0);

        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);

            if (!currentBlock.getPreviousBlockHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (!isValidBlock(previousBlock.getHash(), currentBlock)) {
                return false;
            }

            previousBlock = currentBlock;
        }

        return true;
    }

    public void addBlock(Block block) {
        // substituido pelo de baixo
        //if (block.isValidBlock(block.getPreviousBlockHash()) && block.getPreviousBlockHash().equals(this.getLastBlock().getHash())) {
        if (isValidBlock(this.getLastBlock().getHash(), block)) {
            this.chain.add(block);
            this.currentTransactions.addAll(block.getTransactions());
        }
    }

    public Block getLastBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public List<Block> getChain() {
        return this.chain;
    }

    public List<Transaction> getTransactions() {
        return this.currentTransactions;
    }
    public boolean validateProofOfStake(String validatorAddress) {
        // Example implementation of PoS validation with stake and ownership checks

        // Check if the given validator address is in the list of validators
        if (!validators.containsKey(validatorAddress)) {
            return false;
        }

        // Check if the validator owns enough stake to create a new block
        // You can modify this check based on your specific stake requirements

        if (validators.get(validatorAddress) < this.minStakerequired) {
            return false;
        }

        // Additional checks for ownership of cryptocurrency can be added here
        // For example, you could check if the validator has a certain amount of cryptocurrency in their wallet

        return true;
    }

    public void addValidator(User validator) {
        // Add a validator with the given address and stake to the list of validators
        validators.put(validator, validator.getBalance());
    }

    public void removeValidator(User validator) {
        // Add a validator with the given address and stake to the list of validators
        validators.remove(validator);
    }

    public void checkAddValidator(User validator) {
        // Always add validator to update the balance
        //if ( !validators.containsKey(validator) && (validator.getBalance() >= minStakerequired))
        if ((validator.getBalance() >= minStakerequired))
            addValidator(validator);
    }

    public void checkRemoveValidator(User validator) {
        if (validator.getBalance() < minStakerequired)
            removeValidator(validator);
    }

    public Map<User, Double> getValidators() {
        return this.validators;
    }

}
