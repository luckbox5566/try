package enjoyor.enjoyorzemobilehealth.entities;

/**
 * Created by chenlikang
 */

public class BRLBBC {
    private String BingRenZYID;
    private String IsFever;
    private String Amount;


    public String getAmount() {
        return Amount;
    }

    public String getBingRenZYID() {
        return BingRenZYID;
    }

    public String getIsFever() {
        return IsFever;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setBingRenZYID(String bingRenZYID) {
        BingRenZYID = bingRenZYID;
    }

    public void setIsFever(String isFever) {
        IsFever = isFever;
    }
}
