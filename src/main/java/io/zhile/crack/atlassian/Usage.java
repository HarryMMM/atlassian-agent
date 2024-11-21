package io.zhile.crack.atlassian;

import io.zhile.crack.atlassian.keygen.Encoder;
import io.zhile.crack.atlassian.license.LicenseProperty;
import io.zhile.crack.atlassian.license.products.Bamboo;
import io.zhile.crack.atlassian.license.products.Bitbucket;
import io.zhile.crack.atlassian.license.products.Capture;
import io.zhile.crack.atlassian.license.products.Confluence;
import io.zhile.crack.atlassian.license.products.Crowd;
import io.zhile.crack.atlassian.license.products.Crucible;
import io.zhile.crack.atlassian.license.products.FishEye;
import io.zhile.crack.atlassian.license.products.JIRACore;
import io.zhile.crack.atlassian.license.products.JIRAServiceDesk;
import io.zhile.crack.atlassian.license.products.JIRASoftware;
import io.zhile.crack.atlassian.license.products.Portfolio;
import io.zhile.crack.atlassian.license.products.Questions;
import io.zhile.crack.atlassian.license.products.TeamCalendars;
import io.zhile.crack.atlassian.license.products.ThirdPlugin;
import io.zhile.crack.atlassian.license.products.Training;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 *
 * <p>
 * @author haorui.hao
 * @since 2024/11/21 13:58
 **/
public class Usage {
  private static final String VERSION = "v1.3.1";
    private static final Options OPTIONS = new Options();
    private static final Map<String, String> PRODUCTS = new HashMap(16);
    private static final String PRODUCTS_DESC;

    public Usage() {
    }

    public static void main(String[] args) {
        String usage =
                "\n====================================================\n=======     Atlassian Crack Agent " + VERSION + "     =======\n=======           https://zhile.io           =======\n=======          QQ Group: 30347511          =======\n====================================================\n\n";
        System.out.print(usage);
        System.out.flush();
        OPTIONS.addRequiredOption("p", "product", true, "License product, support: " + PRODUCTS_DESC);
        OPTIONS.addRequiredOption("s", "serverid", true, "License server ID");
        OPTIONS.addRequiredOption("m", "mail", true, "License email");
        OPTIONS.addRequiredOption("o", "organisation", true, "License organisation");
        OPTIONS.addOption("n", "name", true, "License name[default: <license email>]");
        OPTIONS.addOption("d", "datacenter", false, "Data center license[default: false]");
        OPTIONS.addOption("h", "help", false, "Print help message");

        CommandLine command;
        try {
            command = (new DefaultParser()).parse(OPTIONS, args);
        } catch (ParseException var4) {
            printUsage();
            return;
        }

        runCommand(command);
    }

    private static void printUsage() {
        String selfPath = Usage.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.print("KeyGen ");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar " + selfPath, OPTIONS, true);
        System.out.println("\n================================================================================");
        System.out.println("\n# Crack agent usage: append -javaagent arg to system environment: JAVA_OPTS.");
        System.out.println("# Example(execute this command or append it to setenv.sh/setenv.bat file): \n");
        System.out.println("  export JAVA_OPTS=\"-javaagent:" + selfPath + " ${JAVA_OPTS}\"");
        System.out.println("\n# Then start your confluence/jira server.\n");
        System.exit(1);
    }

    private static void runCommand(CommandLine commandLine) {
        if (commandLine.hasOption("h")) {
            printUsage();
        } else {
            String product = commandLine.getOptionValue("p");
            String serverID = commandLine.getOptionValue("s");
            String contactEMail = commandLine.getOptionValue("m");
            String organisation = commandLine.getOptionValue("o");
            String contactName = commandLine.hasOption("n") ? commandLine.getOptionValue("n") : contactEMail;
            boolean dataCenter = commandLine.hasOption("d");
            Object property;
            switch (product) {
                case "conf":
                    property = new Confluence(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "jira":
                    property = new JIRASoftware(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "questions":
                    property = new Questions(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "tc":
                    property = new TeamCalendars(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "bamboo":
                    property = new Bamboo(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "bitbucket":
                    property = new Bitbucket(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "fisheye":
                    property = new FishEye(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "crucible":
                    property = new Crucible(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "crowd":
                    property = new Crowd(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "jc":
                    property = new JIRACore(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "portfolio":
                    property = new Portfolio(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "jsd":
                case "jsm":
                    property = new JIRAServiceDesk(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "training":
                    property = new Training(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                case "capture":
                    property = new Capture(contactName, contactEMail, serverID, organisation, dataCenter);
                    break;
                default:
                    property = new ThirdPlugin(contactName, contactEMail, serverID, organisation, dataCenter);
                    ((ThirdPlugin)property).setProductName(product);
            }

            try {
                ((LicenseProperty)property).init();
                String licenseCode = Encoder.encode(((LicenseProperty)property).toString());
                System.out.println("Your license code(Don't copy this line!!!): \n");
                System.out.println(licenseCode);
                System.out.println();
            } catch (Exception var10) {
                Exception e = var10;
                e.printStackTrace();
            }

            System.out.flush();
        }
    }

    static {
        PRODUCTS.put("conf", "Confluence");
        PRODUCTS.put("jira", "JIRA Software(common jira)");
        PRODUCTS.put("questions", "Questions plugin for Confluence");
        PRODUCTS.put("tc", "Team Calendars plugin for Confluence");
        PRODUCTS.put("bamboo", "Bamboo");
        PRODUCTS.put("bitbucket", "Bitbucket");
        PRODUCTS.put("fisheye", "FishEye");
        PRODUCTS.put("crucible", "Crucible");
        PRODUCTS.put("crowd", "Crowd");
        PRODUCTS.put("jc", "JIRA Core");
        PRODUCTS.put("portfolio", "Portfolio plugin for JIRA");
        PRODUCTS.put("jsd", "JIRA Service Desk");
        PRODUCTS.put("jsm", "JIRA Service Management");
        PRODUCTS.put("training", "Training plugin for JIRA");
        PRODUCTS.put("capture", "Capture plugin for JIRA");
        PRODUCTS.put("*", "Third party plugin key, looks like: com.foo.bar");
        StringBuilder sb = new StringBuilder();
        Iterator var1 = PRODUCTS.entrySet().iterator();

        while(var1.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var1.next();
            sb.append("\n[");
            sb.append((String)entry.getKey());
            sb.append(": ");
            sb.append((String)entry.getValue());
            sb.append("]");
        }

        PRODUCTS_DESC = sb.toString();
    }
}
