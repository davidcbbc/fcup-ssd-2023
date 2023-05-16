package AuctionMechanism;

import AuctionMechanism.TransactionTypes.BidAuctionTransaction;
import AuctionMechanism.TransactionTypes.CloseAuctionTransaction;
import AuctionMechanism.TransactionTypes.CreateAuctionTransaction;
import AuctionMechanism.TransactionTypes.Transaction;
import AuctionMechanism.Wallet.Wallet;
import AuctionMechanism.util.Item;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;
    private List<BidAuctionTransaction> maxBids;
    //private List<Transaction> mempoolTransactions; Tem ou não tem uma mempool na blockchain??!
    private boolean useProofOfStake; // Added field for proof of stake
    private static  double minStakerequired = 1000; // Example: Minimum required stake to create a block is 1000
    private Map<Wallet, Double> validators; // Map to store validator addresses and their stakes

    public Blockchain(int difficulty, boolean useProofOfStake) {
        this.chain = new ArrayList<Block>();
        this.difficulty = difficulty;
        //this.mempoolTransactions = new ArrayList<Transaction>();
        this.maxBids = new ArrayList<BidAuctionTransaction>();
        this.chain.add(createGenesisBlock());
        this.useProofOfStake = useProofOfStake; // Initialize useProofOfStake field
        if (useProofOfStake) {
            validators = new HashMap<>();
        }
    }

    private Block createGenesisBlock() {
        return new Block(new ArrayList<Transaction>(),  0,"0",99999, "kkdfkdfdskjfrhrerueieieif");
    }


    public void printBlockchain() {

        System.out.println("INICIO_Blockchain: " + this.toString());
        System.out.println("main.Blockchain.main.Blockchain:Difficulty: " + this.difficulty);
        System.out.println("main.Blockchain.main.Blockchain:Blocks_INICIO ");
        int count = 0;
        for(Block block : this.chain)
        {
            block.printBlock();
            count++;
        }
        System.out.println("main.Blockchain.main.Blockchain:Blocks_FIM: " + count + " BLOCKS");
        System.out.println("main.Blockchain.main.Blockchain:Transactions_INICIO ");
        count = 0;
        for(Transaction transaction : this.getAllTransactions())
        {
            System.out.println("main.Blockchain.main.Blockchain: Dentro do getAllTransactions():" + transaction.toString());
            //transaction.printTransaction();
            count++;
        }
        System.out.println("main.Blockchain.main.Blockchain:Transactions_FIM: " + count + " Transactions");

        System.out.println("FIM_Blockchain: " + this.toString());
    }

    public List<BidAuctionTransaction> getMaxBids() {
        return maxBids;
    }

    public void setMaxBids(List<BidAuctionTransaction> maxBids) {
        this.maxBids = maxBids;
    }

    // To check why failed Fabio
    public boolean isTransactionValid(Transaction transaction) {
        return transaction.verifySignature();
    }



    //public void addPendingTransaction(Transaction transaction) {

    //    this.pendingTransactions.add(transaction);
    //}


/*
    public main.Blockchain.main.Blockchain.Block mineBlock(List<main.Blockchain.Transaction> pendingTransactions, main.Blockchain.User miner) {
        List<main.Blockchain.Transaction> transactions = new ArrayList<>();
        transactions.addAll(pendingTransactions); //Adicionar ao current_transaction? PEDRO
        //transactions.add(main.Blockchain.Transaction.rewardTransaction(miner.getAddress()));
        main.Blockchain.main.Blockchain.Block block = new main.Blockchain.main.Blockchain.Block(transactions, 0, this.getLastBlock().getHash());
        block.mineBlock(this.difficulty);
        return block;
    }
*/
    // With Proof of Stake
    public Block mineBlock(List<Transaction> mempoolTransactions, Wallet wallet) {

        //System.out.println(this.toString() + " Blockchain.mineBlock vai validar para wallet: " + wallet.toString());
        //System.out.println(this.toString() + " Blockchain.mineBlock com este numero de transactions: " + mempoolTransactions.size());
        if (useProofOfStake && this.validators.containsKey(wallet.getPublicKey()) && getWalletBalance(mempoolTransactions, wallet.getPublicKey()) >= minStakerequired) {
            List<Transaction> transactions = new ArrayList<>();
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoS e vai addicionar as transacções da mempool: " + wallet.toString());
            transactions.addAll(mempoolTransactions);
            // Add reward transaction for main.Blockchain.User
            //transactions.add(Transaction.rewardTransaction(miner));
            //miner.subtractBalance(1); // Decrease stake by 1 for successful block creation
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoS e tenta criar bloco para wallet: " + wallet.toString());
            Block block = new Block(transactions, new Date().getTime(), this.getLastBlock().getHash());
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoS e vai fazer mineBlock do Bloco criado: " + block.toString());
            block.mineBlock(this.difficulty);
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoS e vai fazer addBlock após o mineBlock: " + block.toString());
            addBlock(block);
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoS e faz return do bloco: " + block.toString());
            return block;
        } else if (!useProofOfStake) {
            // If not using PoS, mine the block as usual
            List<Transaction> transactions = new ArrayList<>();
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoW e vai addicionar as transacções da mempool: " + wallet.toString());
            transactions.addAll(mempoolTransactions);
            //transactions.add(Transaction.rewardTransaction(miner));
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoW e tenta criar bloco para wallet: " + wallet.toString());
            Block block = new Block(transactions, new Date().getTime(), this.getLastBlock().getHash());
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoW e vai fazer mineBlock do Bloco criado: " + block.toString());
            block.mineBlock(this.difficulty);
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoW e vai fazer addBlock após o mineBlock: " + block.toString());
            addBlock(block);
            //System.out.println(this.toString() + " Blockchain.mineBlock usa PoW e faz return do bloco: " + block.toString());
            return block;
        }
        //else if validator does not meet PoS requirements, return null
        System.out.println("main.Blockchain.User does not meet PoS requirements!");
        return null;

      /*

        main.Blockchain.main.Blockchain.Block block = new main.Blockchain.main.Blockchain.Block(transactions, new Date().getTime(), this.getLastBlock().getHash());
        block.mineBlock(this.difficulty);
        return block;


        //only checks if the buyer of the transaction still has enough balance to be in the Validators
                    if (transaction instanceof CloseAuctionTransaction) {
                        // checks if balance < minStakerequired
                        if (transaction.)
                        //blockchain.checkRemoveValidator(transaction.getSender());

                    }

      */
    }


    public boolean isValidBlock(String previousBlockHash, Block block) {
        String hash = block.calculateHash();
        String prefix = "0".repeat(this.difficulty);
        return block.getPreviousBlockHash().equals(previousBlockHash) && hash.substring(0, this.difficulty).equals(prefix);
    }

    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        // Check that the block's previous hash matches the hash of the last block
        if (!newBlock.getPreviousBlockHash().equals(previousBlock.getHash())) {
            //System.out.println(" isValidNewBlock: newBlock.getPreviousBlockHash: " + newBlock.getPreviousBlockHash() + " previousBlockHash:" + previousBlock.getHash() );
            return false;
        }

        // Check that the block's hash is correct
        String hash = newBlock.calculateHash();
        if (!hash.equals(newBlock.getHash())) {
            //System.out.println(" isValidNewBlock: hash: " + hash + " newBlockHash:" + newBlock.getHash() );
            return false;
        }

        // Check that the block's hash satisfies the difficulty requirement
        String target = new String(new char[difficulty]).replace('\0', '0');
        if (!hash.substring(0, difficulty).equals(target)) {
            //System.out.println(" isValidNewBlock: a ultima validação"  );
            return false;
        }
        // PEDRO123 Verify the transactions in the block
        for (Transaction transaction : newBlock.getTransactions()) {
            if (!transaction.verifySignature()) {
                System.out.println("A transaction in the block has an invalid signature");
                return false;
            }
        }

        // All checks passed, so the block is valid
        return true;
    }

    /*
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
*/
    public boolean replaceChain(List<Block> newChain) {
        if (isChainLengthValid(newChain) && isChainValid(newChain)) {
            this.chain = newChain;
            //this.pendingTransactions = new ArrayList<Transaction>();
            return true;
        } else {
            return false;
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
        //System.out.println(this.toString() + " Blockchain.addBlock vai validar se é valido: " + block.toString());
        //System.out.println("Vai percorrer a blockchain:");
        //printBlockchain();
        //System.out.println(this.toString() + " Blockchain.addBlock vai validar. getLastBlockHash: " + this.getLastBlock().getHash());
        //System.out.println(this.toString() + " Blockchain.addBlock vai validar. BlockHash: " + block.getPreviousBlockHash());
        if (isValidNewBlock(block, this.getLastBlock())) {
            //System.out.println(this.toString() + " Blockchain.addBlock adicionou bloco valido com sucesso: " + block.toString());
            this.chain.add(block);
        } else {
            System.out.println(this.toString() + " Blockchain.addBlock Attempted to add invalid block to blockchain: " + block.toString());
        }
    }

    public Block getLastBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public List<Block> getChain() {
        return this.chain;
    }

    //public List<Transaction> getPendingTransactions() {
    //    return this.pendingTransactions;
    //}

    /*
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

     */
    public void addValidator(Wallet validator, List<Transaction> mempoolTransactions) {
        // Add a validator with the given address and stake to the list of validators
        //validators.put(validator, Double.valueOf( validator.getBalance()));
        validators.put(validator, Double.valueOf( getWalletBalance(mempoolTransactions,validator.getPublicKey())));
    }

    public void removeValidator(Wallet validator) {
        // Add a validator with the given address and stake to the list of validators
        validators.remove(validator);
    }

    public void checkAddValidator(Wallet validator, List<Transaction> mempoolTransactions) {
        // Always add validator to update the balance
        //if ( !validators.containsKey(validator) && (validator.getBalance() >= minStakerequired))
        if ((getWalletBalance(mempoolTransactions, validator.getPublicKey()) >= minStakerequired))
            addValidator(validator, mempoolTransactions);
    }

    public void checkRemoveValidator(Wallet validator, List<Transaction> mempoolTransactions) {
        if (getWalletBalance(mempoolTransactions, validator.getPublicKey())< minStakerequired)
            removeValidator(validator);
    }

    public Map<Wallet, Double> getValidators() {
        return this.validators;
    }

    public boolean getUseProofOfStake() {
        return this.useProofOfStake;
    }


    private float getWalletBalance(List<Transaction> mempoolTransactions, PublicKey publicKey) {
        // check closed transactions
        float balance = 100;

        //Need to get transactions from the blocks and not this list that must be the Transactions not yet in the Blockchain Blocks
        for (Transaction tr : this.getAllTransactions()) {

            if (tr instanceof CloseAuctionTransaction) {

                CloseAuctionTransaction closeTransaction = (CloseAuctionTransaction) tr;
                if ( closeTransaction.getWinnerPublicKey().equals(publicKey) ) {
                    balance = balance - closeTransaction.getWinningBid();
                } else if ( closeTransaction.getSellerPublicKey().equals(publicKey) ) {
                    balance = balance + closeTransaction.getWinningBid();
                }
            }
        }
        //Check also memPool Transactions
        for (Transaction tr : mempoolTransactions) {

            if (tr instanceof CloseAuctionTransaction) {

                CloseAuctionTransaction closeTransaction = (CloseAuctionTransaction) tr;
                if ( closeTransaction.getWinnerPublicKey().equals(publicKey) ) {
                    balance = balance - closeTransaction.getWinningBid();
                } else if ( closeTransaction.getSellerPublicKey().equals(publicKey) ) {
                    balance = balance + closeTransaction.getWinningBid();
                }
            }
        }
        // check openMaxBids of current open Auctions
        for (BidAuctionTransaction bidTr : this.maxBids) {
            if ( bidTr.getBuyerPublicKey().equals(publicKey) ) {
                balance = balance - bidTr.getBidAmount();
            } else if ( bidTr.getSellerPublicKey().equals(publicKey) ) {
                balance = balance + bidTr.getBidAmount();
            }
        }
        return balance;
    }


    /*public void addTransaction(Transaction transaction) {
        // Validate the transaction
        if (transactionValid(transaction)) {
            mempoolTransactions.add(transaction);
        } else {
            // Handle invalid transaction
        }
    } */
    /*public boolean transactionValid(Transaction transaction) {
        // Check if the transaction signature is valid
        if (!transaction.verifySignature()) {
            return false;
        }

        if (transaction instanceof CreateAuctionTransaction) {
            // The sender doesn't need enough balance to create an auction
            return true;
        }

        if (transaction instanceof CloseAuctionTransaction) {
            // The sender doesn't need enough balance to create an auction
            return true;
        }

        if (transaction instanceof BidAuctionTransaction) {
            BidAuctionTransaction bid = (BidAuctionTransaction) transaction;


            // Check if the Buyer has enough balance
            double senderBalance = getWalletBalance(bid.getBuyerPublicKey());
            if (senderBalance < bid.getBidAmount()) {
                return false;
            }

            // Check if the auction exists and is open
            if (!(checkAuctionOpen(bid.getAuctionedItem()))) {
                return false;
            }

            // Check if the bid is higher than the current highest bid
            BidAuctionTransaction highestBid = getHighestBid(bid.getAuctionedItem());
            if (highestBid != null && bid.getBidAmount() <= highestBid.getBidAmount()) {
                return false;
            }
        }
        return true;
    }
    */
    /*public boolean checkAuctionOpen(Item auctionedItem) {
        boolean flag = false;

        List<Transaction> allTransactions = this.getAllTransactions();
        for (Transaction tr : allTransactions) {
            if (tr instanceof CreateAuctionTransaction) {
                CreateAuctionTransaction createTr = (CreateAuctionTransaction) tr;
                if (createTr.getAuctionedItem().equals(auctionedItem)) {
                    flag = true;
                }
            }
            if (tr instanceof CloseAuctionTransaction) {
                CloseAuctionTransaction closeTr = (CloseAuctionTransaction) tr;
                if (closeTr.getAuctionedItem().equals(auctionedItem)) {
                    flag = false;
                }
            }
        }

        return flag;
    }*/


    public List<Transaction> getAllTransactions() {
        List<Transaction> trs = new ArrayList<>();
        for (Block block : chain) {
            //System.out.println(this.toString() + " Blockchain.getAllTransactions o bloco: " + block.toString());
            List<Transaction> tr = block.getTransactions();
            //System.out.println(this.toString() + " Blockchain.getAllTransactions o bloco: " + block.toString() + "numero de transactions:" + tr.size());
            trs.addAll(tr);
        }
        // Mempool transactions not included, should be added where need by who need it
        //trs.addAll(mempoolTransactions);
        return trs;
    }

    /*public BidAuctionTransaction getHighestBid(Item auctionedItem) {
        BidAuctionTransaction highestBid = null;
        List<Transaction> allTransactions = this.getAllTransactions();
        for (Transaction tr : allTransactions) {
            if (tr instanceof BidAuctionTransaction) {
                BidAuctionTransaction bidTr = (BidAuctionTransaction) tr;
                if (bidTr.getAuctionedItem().equals(auctionedItem)) {
                    if (highestBid == null || bidTr.getBidAmount() > highestBid.getBidAmount()) {
                        highestBid = bidTr;
                    }
                }
            }
        }
        return highestBid;
    }*/

    public List<Transaction> getTransactionsByType(Class<? extends Transaction> transactionType) {
        List<Transaction> trs_output = new ArrayList<>();
        //checks all blocks
        for (Block block : chain) {
            List<Transaction> trs = block.getTransactions();
            //checks all transactions
            for ( Transaction tr : trs) {
                //Only the specified transaction type is added
                if (transactionType.isInstance(tr)) {
                    trs_output.add(tr);
                }
            }

        }
        return trs_output;
    }


    // TO CHECK: When an Auction is closed, we need to check if the buyer still has:
    //  an amount > minStakerequired to be in the Validators list




}
