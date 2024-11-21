package io.zhile.crack.atlassian.license.products;

import io.zhile.crack.atlassian.license.LicenseProperty;

/**
 * @author pengzhile
 * @version 1.0
 * @link https://zhile.io
 */
abstract public class Plugin extends LicenseProperty {
    Plugin(String ContactName, String ContactEMail, String ServerID, String Organisation, boolean dataCenter) {
        super(ContactName, ContactEMail, ServerID, Organisation, dataCenter);
    }

    Plugin(String ContactName, String ContactEMail, String ServerID, String Organisation) {
        this(ContactName, ContactEMail, ServerID, Organisation, false);
    }

    public void init() {
        super.init();
        this.setLicenseID((String) null);
        this.setEnterprise(true);
    }

    @Override
    public void setNumberOfUsers(int numberOfUsers) {
        data.put("NumberOfUsers", String.valueOf(numberOfUsers));
    }

    public void setEnterprise(boolean enterprise) {
        data.put(productProperty("enterprise"), String.valueOf(enterprise));
    }
}
