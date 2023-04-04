import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;
    private List<Transaction> currentTransactions;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<Block>();
        this.difficulty = difficulty;
        this.currentTransactions = new ArrayList<Transaction>();
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block(new ArrayList<Transaction>(), 0,"0");
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
    public Block mineBlock(User miner) {
        List<Transaction> transactions = new ArrayList<Transaction>(this.currentTransactions);
        //transactions.add(Transaction.rewardTransaction(miner.getAddress()));
        Block block = new Block(transactions, 0,this.getLastBlock().getHash());
        block.mineBlock(this.difficulty);
        this.chain.add(block);
        this.currentTransactions = new ArrayList<Transaction>();
        return block;
    }

    */
    public Block mineBlock(List<Transaction> pendingTransactions, User miner) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(pendingTransactions); //Adicionar ao current_transaction? PEDRO
        //transactions.add(Transaction.rewardTransaction(miner.getAddress()));
        Block block = new Block(transactions, 0, this.getLastBlock().getHash());
        block.mineBlock(this.difficulty);
        return block;
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
/* substituido pelo de baixo
            if (!currentBlock.isValidBlock(currentBlock.getPreviousBlockHash())) {
                return false;
            } */

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
/* substituido pelo de baixo

            if (!currentBlock.isValidBlock(currentBlock.getPreviousBlockHash())) {
                return false;
            }
  */
            if (!isValidBlock(previousBlock.getHash(), currentBlock)) {
                return false;
            }

            previousBlock = currentBlock;
        }

        return true;
    }

    public void addBlock(Block block) {
        // csubstituido pelo de baixo
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
}
