package io.zhile.crack.atlassian.license.products;

/**
 * @author pengzhile
 * @version 1.0
 * @link https://zhile.io
 */
public class Training extends Plugin {
    public Training(String ContactName, String ContactEMail, String ServerID, String Organisation, boolean dataCenter) {
        super(ContactName, ContactEMail, ServerID, Organisation, dataCenter);
    }

    public void init() {
        super.init();
        this.setSubscription(true);
    }


    @Override
    public String getProductName() {
        return "atlassian-jira-training";
    }

    public void setSubscription(boolean subscription) {
        data.put("Subscription", String.valueOf(subscription));
    }
}
