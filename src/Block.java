import java.util.List;

public class Block {
    private List<Transaction> transactions;
    private long timestamp;
    private String previousBlockHash;
    private String hash;
    private int nonce;

    public Block(List<Transaction> transactions, long timestamp, String previousBlockHash) {
        this.transactions = transactions;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.hash = calculateHash();
    }

    public String getHash() {
        return this.hash;
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                this.previousBlockHash +
                        Long.toString(this.timestamp) +
                        Integer.toString(this.nonce) +
                        this.transactions.toString()
        );
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }
    public void mineBlock(int difficulty) {
        String target = StringUtil.getDifficultyString(difficulty);
        while (!this.hash.substring(0, difficulty).equals(target)) {
            this.nonce++;
            this.hash = calculateHash();
        }
        System.out.println("Block mined! : " + this.hash);
    }

    public boolean isValidBlock(String previousBlockHash) {
        return this.previousBlockHash.equals(previousBlockHash) &&
                this.hash.equals(calculateHash());
    }
}

