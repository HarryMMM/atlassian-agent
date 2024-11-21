package io.zhile.crack.atlassian.license;

import io.zhile.crack.atlassian.keygen.Encoder;
import io.zhile.crack.atlassian.utils.Base64;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public abstract class LicenseProperty {
    protected Date date = new Date();
    protected Map<String, String> data = new HashMap(32);
    protected String contactName;
    protected String contactEMail;
    protected String serverID;
    protected String organisation;
    protected boolean dataCenter;

    public abstract String getProductName();

    public LicenseProperty(String contactName, String contactEMail, String serverID, String organisation, boolean dataCenter) {
        this.contactName = contactName;
        this.contactEMail = contactEMail;
        this.serverID = serverID;
        this.organisation = organisation;
        this.dataCenter = dataCenter;
    }

    public void init() {
        Date expiryDate = new Date(3771590399000L);
        String licenseId = "L" + System.currentTimeMillis();
        this.setActive(true);
        this.setPurchaseDate(this.date);
        this.setLicenseExpiryDate(expiryDate);
        this.setMaintenanceExpiryDate(expiryDate);
        this.setNumberOfUsers(-1);
        this.setStarter(false);
        this.setSEN("SEN-" + licenseId);
        this.setLicenseID("LIDSEN-" + licenseId);
        this.setCreationDate(this.date);
        this.setLicenseType(LicenseType.COMMERCIAL);
        this.setDescription("Unlimited license by https://zhile.io");
        this.setEvaluation(false);
        this.setContactName(this.contactName);
        this.setContactEMail(this.contactEMail);
        this.setServerID(this.serverID);
        this.setOrganisation(this.organisation);
        this.setDataCenter(this.dataCenter);
        this.setLicenseVersion("2");
        this.setKeyVersion("1600708331");
    }

    public void setDescription(String description) {
        this.data.put("Description", description);
    }

    public void setCreationDate(Date creationDate) {
        this.data.put("CreationDate", (new SimpleDateFormat("yyyy-MM-dd")).format(creationDate));
    }

    public void setContactName(String contactName) {
        this.data.put("ContactName", contactName);
    }

    public void setActive(boolean active) {
        this.data.put(this.productProperty("active"), String.valueOf(active));
    }

    public void setContactEMail(String contactEMail) {
        this.data.put("ContactEMail", contactEMail);
    }

    public void setStarter(boolean starter) {
        this.data.put(this.productProperty("Starter"), String.valueOf(starter));
    }

    public void setEvaluation(boolean evaluation) {
        this.data.put("Evaluation", String.valueOf(evaluation));
    }

    public void setLicenseType(LicenseType licenseType) {
        this.data.put(this.productProperty("LicenseTypeName"), licenseType.toString());
    }

    public void setMaintenanceExpiryDate(Date maintenanceExpiryDate) {
        this.data.put("MaintenanceExpiryDate", (new SimpleDateFormat("yyyy-MM-dd")).format(maintenanceExpiryDate));
    }

    public void setOrganisation(String organisation) {
        this.data.put("Organisation", organisation);
    }

    public void setSEN(String SEN) {
        this.data.put("SEN", SEN);
    }

    public void setServerID(String serverID) {
        this.data.put("ServerID", serverID);
    }

    public void setLicenseID(String licenseID) {
        this.data.put("LicenseID", licenseID);
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.data.put("LicenseExpiryDate", (new SimpleDateFormat("yyyy-MM-dd")).format(licenseExpiryDate));
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.data.put(this.productProperty("NumberOfUsers"), String.valueOf(numberOfUsers));
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.data.put("PurchaseDate", (new SimpleDateFormat("yyyy-MM-dd")).format(purchaseDate));
    }

    public void setLicenseVersion(String version) {
        this.data.put("licenseVersion", version);
    }

    public void setKeyVersion(String version) {
        this.data.put("keyVersion", version);
    }

    public void setDataCenter(boolean dataCenter) {
        if (dataCenter) {
            this.data.put(this.productProperty("DataCenter"), "true");
            this.data.put("Subscription", "true");
        } else {
            this.data.remove(this.productProperty("DataCenter"));
            this.data.remove("Subscription");
        }

    }

    protected String productProperty(String property) {
        return this.getProductName() + "." + property;
    }

    protected void setLicenseHash() {
        this.data.remove("licenseHash");
        StringBuilder sb = new StringBuilder();
        Iterator var2 = (new TreeSet(this.data.keySet())).iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            String val = (String)this.data.get(key);
            if (val != null) {
                sb.append(this.escape(key, true)).append("=").append(this.escape(val, false)).append("\n");
            }
        }

        try {
            this.data.put("licenseHash", Base64.encode(Encoder.sign(sb.toString().getBytes(StandardCharsets.UTF_8))));
        } catch (Exception var5) {
        }

    }

    protected String escape(String str, boolean isKey) {
        int len = str.length();
        StringBuilder sb = new StringBuilder(len * 2);

        for(int index = 0; index < len; ++index) {
            char c = str.charAt(index);
            switch (c) {
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case ' ':
                    if (index == 0 || isKey) {
                        sb.append('\\');
                    }

                    sb.append(' ');
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    if ("=: \t\r\n\f#!".indexOf(c) != -1) {
                        sb.append('\\');
                    }

                    sb.append(c);
            }
        }

        return sb.toString();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("#");
        sb.append(this.date.toString());
        this.setLicenseHash();
        Iterator var2 = this.data.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var2.next();
            if (entry.getValue() != null) {
                sb.append("\n");
                sb.append((String)entry.getKey());
                sb.append("=");
                sb.append((String)entry.getValue());
            }
        }

        return sb.toString();
    }
}