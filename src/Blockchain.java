import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private int difficulty;
    private List<Block> chain;
    private List<Transaction> currentTransactions;

    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
        this.chain = new ArrayList<>();
        this.currentTransactions = new ArrayList<>();
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block(new ArrayList<>(), System.currentTimeMillis(), "0");
    }

    public List<Block> getChain() {
        return chain;
    }

    public List<Transaction> getTransactions() {
        return currentTransactions;
    }

    public void addTransaction(Transaction transaction) {
        currentTransactions.add(transaction);
    }

    public boolean isValidBlock(Block block) {
        String expectedBlockHash = block.calculateHash();
        String actualBlockHash = block.getHash();

        if (!actualBlockHash.equals(expectedBlockHash)) {
            System.out.println("Invalid block hash: " + actualBlockHash + " (expected " + expectedBlockHash + ")");
            return false;
        }

        String previousBlockHash = block.getPreviousBlockHash();
        int previousBlockIndex = chain.indexOf(block) - 1;

        if (previousBlockIndex >= 0) {
            Block previousBlock = chain.get(previousBlockIndex);
            String expectedPreviousBlockHash = previousBlock.calculateHash();
            if (!previousBlockHash.equals(expectedPreviousBlockHash)) {
                System.out.println("Invalid previous block hash: " + previousBlockHash + " (expected " + expectedPreviousBlockHash + ")");
                return false;
            }
        }

        return true;
    }

    public Block mineBlock(List<Transaction> transactions, User miner) {
        long timestamp = System.currentTimeMillis();
        String previousBlockHash = chain.get(chain.size() - 1).getHash();
        Block block = new Block(transactions, timestamp, previousBlockHash);
        block.mineBlock(difficulty);
        if (isValidBlock(block)) {
            chain.add(block);
            return block;
        } else {
            return null;
        }
    }

    public void addBlock(Block block) {
        chain.add(block);
    }

    public boolean isValidChain() {
        String previousBlockHash = "0";
        for (Block block : chain) {
            if (!block.isValidBlock(previousBlockHash)) {
                return false;
            }
            previousBlockHash = block.getHash();
        }
        return true;
    }

    public void replaceChain(List<Block> newChain) {
        if (isChainValid(newChain) && isChainLengthValid(newChain)) {
            chain = newChain;
            currentTransactions.clear();
        }
    }

    public boolean isChainLengthValid(List<Block> chain) {
        return chain.size() > this.chain.size();
    }

    public boolean isChainValid(List<Block> chain) {
        String previousBlockHash = "0";
        for (Block block : chain) {
            if (!block.isValidBlock(previousBlockHash)) {
                return false;
            }
            previousBlockHash = block.getHash();
        }
        return true;
    }
}
