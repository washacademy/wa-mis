package routines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class ReportingHelper {

    private static final Logger LOGGER = Logger
            .getLogger(ReportingHelper.class);

    /**
     * This method return operator ID from table operator dimension.
     */
    public static Long getOperator(String operatorCode,
            Connection reportDBConn, Map<String, Object> globalMap) {

        Map<String, Long> operatorMap = null;
        if (globalMap.containsKey("operatorMap")) {
            operatorMap = (Map) globalMap.get("operatorMap");
            if (operatorMap.containsKey(operatorCode)) {
                return operatorMap.get(operatorCode);
            }
        } else {
            operatorMap = new HashMap<String, Long>();
            globalMap.put("operatorMap", operatorMap);
        }
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        Long operatorId = null;
        try {
            stmt1 = reportDBConn
                    .prepareStatement("SELECT ID FROM dim_operator where operator_code=?");
            stmt1.setString(1, operatorCode);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                operatorId = rs1.getLong(1);
            } else {
                // Insert record
                stmt2 = reportDBConn
                        .prepareStatement(
                                "INSERT INTO dim_operator(Operator,operator_code,DW_LastUpdatedDtim) VALUES(?, ?, now())",
                                Statement.RETURN_GENERATED_KEYS);
                stmt2.setString(1, operatorCode);
                stmt2.setString(2, operatorCode);
                stmt2.executeUpdate();
                rs2 = stmt2.getGeneratedKeys();
                if (rs2.next()) {
                    operatorId = rs2.getLong(1);
                    LOGGER.warn("INSERTED NEW OPERATOR: " + operatorCode);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (rs2 != null)
                    rs2.close();
                if (stmt1 != null)
                    stmt1.close();
                if (stmt2 != null)
                    stmt2.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        operatorMap.put(operatorCode, operatorId);
        return operatorId;
    }

    /**
     * This method return Campaign ID from table Campaign dimension.
     */
    public static Long getCampaignId(String weekId, String packName,
            int messagesPerWeek, Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        ResultSet rs1 = null;
        Long campaignId = null;
        try {
            if (("48WeeksPack".equalsIgnoreCase(packName))
                    && (!"w1_1".equalsIgnoreCase(weekId))) {
                if (weekId.length() == 4) {
                    weekId = String.format("w%d_%s",
                            Integer.valueOf(weekId.substring(1, 2)) + 24,
                            weekId.charAt(weekId.length() - 1));
                } else {
                    weekId = String.format("w%d_%s",
                            Integer.valueOf(weekId.substring(1, 3)) + 24,
                            weekId.charAt(weekId.length() - 1));
                }
            }
            stmt1 = reportDBConn
                    .prepareStatement("SELECT ID FROM campaign_dimension WHERE Campaign_ID=?");
            stmt1.setString(1, weekId);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                campaignId = rs1.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return campaignId;
    }

    /**
     * This method return WEEK NUMBER And Update Status Transition View table
     * for first week.
     */
    public static Integer getWeekAndUpdateTransitionView(Long campaignId,
            String subscriptionPackName, Long subscriptionId,
            java.util.Date lastModifyDate, Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        ResultSet rs1 = null;
        String weekId = null;
        Integer weekNumber = null;
        try {
            stmt1 = reportDBConn
                    .prepareStatement("SELECT Campaign_ID FROM campaign_dimension WHERE ID=?");
            stmt1.setLong(1, campaignId);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                weekId = rs1.getString(1);
            }
            if (null != weekId) {
                if (weekId.length() == 4) {
                    weekNumber = Integer.valueOf(weekId.substring(1, 2));// TODO
                                                                         // from
                                                                         // regex
                } else {
                    weekNumber = Integer.valueOf(weekId.substring(1, 3));
                }
                if (("48WeeksPack".equalsIgnoreCase(subscriptionPackName))
                        && (weekNumber != 1)) {
                    weekNumber = weekNumber - 24;// TODO
                }
            }
            // Removed check for first week and update transition view

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return weekNumber;
    }

    /**
     * This method return DATE ID from table date_dimension.
     */
    public static Integer getDateId(java.util.Date inputDate,
            Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        Integer dateId = null;
        try {
            java.util.Calendar cal = Calendar.getInstance();
            cal.setTime(inputDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());

            stmt1 = reportDBConn
                    .prepareStatement("SELECT ID FROM date_dimension WHERE FULLDATE=?");
            stmt1.setDate(1, sqlDate);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                dateId = rs1.getInt(1);
            } else {
                LOGGER.warn(inputDate + "--- Not exist in date dimension");
                String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY",
                        "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
                String[] monthNames = { "January", "February", "March",
                        "April", "May", "June", "July", "August", "September",
                        "October", "November", "December" };
                stmt2 = reportDBConn
                        .prepareStatement(
                                "INSERT INTO date_dimension(DimDay, DimWeek_NUM, DimMonth, DimYear, FullDate) VALUES(?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                stmt2.setString(1, days[cal.get(Calendar.DAY_OF_WEEK) - 1]);
                stmt2.setInt(2, cal.get(Calendar.WEEK_OF_YEAR));
                stmt2.setString(3, monthNames[cal.get(Calendar.MONTH)]);
                stmt2.setInt(4, cal.get(Calendar.YEAR));
                stmt2.setDate(5, sqlDate);
                stmt2.executeUpdate();
                rs2 = stmt2.getGeneratedKeys();
                if (rs2.next()) {
                    dateId = rs2.getInt(1);
                    LOGGER.info("Record craeted in date dimension with ID="
                            + dateId);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
                if (rs2 != null)
                    rs2.close();
                if (stmt2 != null)
                    stmt2.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return dateId;
    }

    /**
     * This method return FULL DATE from table date_dimension using ID.
     */
    public static java.util.Date getDateFromId(Integer dateId,
            Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        ResultSet rs1 = null;
        java.util.Date fullDate = null;
        try {
            stmt1 = reportDBConn
                    .prepareStatement("SELECT FullDate FROM date_dimension WHERE ID=?");
            stmt1.setInt(1, dateId);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                fullDate = new java.util.Date(rs1.getDate(1).getTime());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return fullDate;
    }

    /**
     * This method return Time ID from table time_dimension.
     */
    public static Integer getTimeId(java.util.Date inputTime,
            Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        ResultSet rs1 = null;
        Integer timeId = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputTime);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            String time = String.format("%02d:%02d:00", hours, minutes);
            stmt1 = reportDBConn
                    .prepareStatement("SELECT ID FROM time_dimension WHERE FULLTime=?");
            stmt1.setString(1, time);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                timeId = rs1.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return timeId;
    }

    /**
     * Enumeration for INBOX CALL STATUS VALUES
     * 
     */
    public enum CallStatusInbox {
        SUCCESS("SUCCESS", 1), FAILED("FAILED", 2), REJECTED("REJECTED", 3);

        private final String name;

        private final Integer id;

        private CallStatusInbox(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        public static String getFor(Integer id) {
            String name = null;
            for (CallStatusInbox callStatusInbox : CallStatusInbox.values()) {
                if (callStatusInbox.id == id) {
                    name = callStatusInbox.name;
                    break;
                }
            }
            return name;
        }

    }

    /**
     * Enumeration for OBD CALL STATUS VALUES
     * 
     */
    public enum CallStatusOBD {
        OBD_SUCCESS_CALL_CONNECTED("1001", "SUCCESS"), OBD_FAILED_NOATTEMPT(
                "2000", "ND"), OBD_FAILED_BUSY("2001", "ND"), OBD_FAILED_NOANSWER(
                "2002", "NA"), OBD_FAILED_SWITCHEDOFF("2003", "SO"), OBD_FAILED_INVALIDNUMBER(
                "2004", "ND"), OBD_FAILED_OTHERS("2005", "ND"), OBD_DNIS_IN_DND(
                "3001", "ND");

        private final String key;

        private final String value;

        private CallStatusOBD(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getFor(String key) {
            String value = null;
            for (CallStatusOBD callStatusOBD : CallStatusOBD.values()) {
                if (callStatusOBD.key.equalsIgnoreCase(key)) {
                    value = callStatusOBD.value;
                    break;
                }
            }
            return value;
        }
    }

    /**
     * Enumeration for Subscription Status
     * 
     */
    public enum SubscriptionStatus {
        ACTIVE, PENDING_ACTIVATION, DEACTIVATED, COMPLETED, DEACTIVATED_BY_USER;
    }

    /**
     * This method return channel ID from table channel dimension.
     */
    public static Long getChannelId(String origin, Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        ResultSet rs1 = null;
        Long channelId = null;
        try {
            stmt1 = reportDBConn
                    .prepareStatement("SELECT ID FROM channel_dimension WHERE Channel=?");
            stmt1.setString(1, origin);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                channelId = rs1.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return channelId;
    }

    public static Integer ageOnService (java.util.Date subscription_start_date,java.util.Date last_change_of_beneficiary){
		Integer weekdiff = null;
		Calendar startCal = Calendar.getInstance();
        startCal.setTime(subscription_start_date);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(last_change_of_beneficiary);
        long diff = endCal.getTimeInMillis()-startCal.getTimeInMillis();
        weekdiff = (int) (diff / (7 * 24 * 60 * 60 * 1000));
    	return weekdiff;
    }
    /**
     * This method add StatusMeasure And TransitionView RecordsFor New
     * Subscription.
     */
    public static Long addStatusMeasureAndTransitionViewRecordsForNewSubscription(
            long subscriptionId, long channelId, long operatorId,
            long subscriptionPackId, String subscriptionStatus,
            java.util.Date lastModifiedTime, java.util.Date creationTime,
            java.util.Date activationDate, java.util.Date startDate,
            Connection reportDBConn) {
        Long weekNumber = null;
        PreparedStatement stmt = null;
        try {
            // calculate week number As per Start Date

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);

            Calendar currentCal = Calendar.getInstance();

            if (startCal.after(currentCal)) {
                weekNumber = 0L;
            } else {
                long diff = currentCal.getTimeInMillis()
                        - startCal.getTimeInMillis();
                long days = diff / (24 * 60 * 60 * 1000);
                weekNumber = (days / 7) + 1;
            }
            java.util.Date creationDateComp = ReportingHelper
                    .getDateWithoutTime(creationTime);
            java.util.Date activationDateComp = ReportingHelper
                    .getDateWithoutTime(activationDate);

            if (SubscriptionStatus.PENDING_ACTIVATION.name().equals(
                    subscriptionStatus)) {
                // pass creation time as last modify time
                insertRecordInSubscriptionStatusMeasure(subscriptionId,
                        channelId, operatorId, subscriptionPackId,
                        SubscriptionStatus.PENDING_ACTIVATION.name(),
                        weekNumber, creationTime, reportDBConn);
                insertRecordInStatusTransitionView(subscriptionId,
                        SubscriptionStatus.PENDING_ACTIVATION.name(), null,
                        creationTime, null, reportDBConn);

            } else if (SubscriptionStatus.ACTIVE.name().equals(
                    subscriptionStatus)) {
                if (activationDate != null) {
                    // Direct Active Record check
                    if (creationDateComp.equals(activationDateComp)) {
                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                SubscriptionStatus.ACTIVE.name(),
                                weekNumber, activationDate, reportDBConn);

                        insertRecordInStatusTransitionView(subscriptionId,
                                SubscriptionStatus.ACTIVE.name(), null,
                                activationDate, null, reportDBConn);
                    } else {
                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                SubscriptionStatus.PENDING_ACTIVATION.name(),
                                weekNumber, creationTime, reportDBConn);
                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                SubscriptionStatus.ACTIVE.name(),
                                weekNumber, activationDate, reportDBConn);

                        insertRecordInStatusTransitionView(subscriptionId,
                                SubscriptionStatus.PENDING_ACTIVATION.name(),
                                SubscriptionStatus.ACTIVE.name(), creationTime,
                                activationDate, reportDBConn);

                        insertRecordInStatusTransitionView(subscriptionId,
                                SubscriptionStatus.ACTIVE.name(), null,
                                activationDate, null, reportDBConn);
                    }
                } else {
                    LOGGER.warn("Activation Date is Null for subscription Id:"
                            + subscriptionId);
                }

            } else if (SubscriptionStatus.DEACTIVATED.name().equals(
                    subscriptionStatus)
                    || SubscriptionStatus.COMPLETED.name().equals(
                            subscriptionStatus)
                    || SubscriptionStatus.DEACTIVATED_BY_USER.name().equals(
                            subscriptionStatus)) {

                if (activationDate != null) {
                    if (creationDateComp.equals(activationDateComp)) {
                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                SubscriptionStatus.ACTIVE.name(),
                                weekNumber, activationDate, reportDBConn);
                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                subscriptionStatus, weekNumber,
                                lastModifiedTime, reportDBConn);

                        insertRecordInStatusTransitionView(subscriptionId,
                                SubscriptionStatus.ACTIVE.name(),
                                subscriptionStatus, activationDate,
                                lastModifiedTime, reportDBConn);
                    } else {
                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                SubscriptionStatus.PENDING_ACTIVATION.name(),
                                weekNumber, creationTime, reportDBConn);

                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                SubscriptionStatus.ACTIVE.name(),
                                weekNumber, activationDate, reportDBConn);

                        insertRecordInSubscriptionStatusMeasure(subscriptionId,
                                channelId, operatorId, subscriptionPackId,
                                subscriptionStatus, weekNumber,
                                lastModifiedTime, reportDBConn);

                        insertRecordInStatusTransitionView(subscriptionId,
                                SubscriptionStatus.PENDING_ACTIVATION.name(),
                                SubscriptionStatus.ACTIVE.name(), creationTime,
                                activationDate, reportDBConn);

                        insertRecordInStatusTransitionView(subscriptionId,
                                SubscriptionStatus.ACTIVE.name(),
                                subscriptionStatus, activationDate,
                                lastModifiedTime, reportDBConn);
                    }
                } else {
                    insertRecordInSubscriptionStatusMeasure(subscriptionId,
                            channelId, operatorId, subscriptionPackId,
                            SubscriptionStatus.PENDING_ACTIVATION.name(),
                            weekNumber, creationTime, reportDBConn);
                    insertRecordInSubscriptionStatusMeasure(subscriptionId,
                            channelId, operatorId, subscriptionPackId,
                            subscriptionStatus, weekNumber, lastModifiedTime,
                            reportDBConn);

                    insertRecordInStatusTransitionView(subscriptionId,
                            SubscriptionStatus.PENDING_ACTIVATION.name(),
                            subscriptionStatus, creationTime, lastModifiedTime,
                            reportDBConn);
                }

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return 0L;
    }

    /**
     * This method return WEEK NUMBER And Insert into Status Transition View
     * table for update subscription record.
     */
    public static Long getWeekForUpdateSubsAndInsertTransitionView(
            java.util.Date startDate, Long subscriptionId,
            String subscriptionStatus, java.util.Date lastModifyDate,
            Connection reportDBConn) {
        Long weekNumber = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        ResultSet rs1 = null;
        PreparedStatement stmt3 = null;
        try {
            // calculate week number

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);

            Calendar currentCal = Calendar.getInstance();

            if (startCal.after(currentCal)) {
                weekNumber = 0L;
            } else {
                long diff = currentCal.getTimeInMillis()
                        - startCal.getTimeInMillis();
                long days = diff / (24 * 60 * 60 * 1000);
                weekNumber = (days / 7) + 1;
            }

            if (SubscriptionStatus.DEACTIVATED.name()
                    .equals(subscriptionStatus)
                    || SubscriptionStatus.COMPLETED.name().equals(
                            subscriptionStatus)
                    || SubscriptionStatus.DEACTIVATED_BY_USER.name().equals(
                            subscriptionStatus)) {

                // Update Active/PA record
                StringBuilder updateQuery = new StringBuilder(
                        " UPDATE status_transition_view ");
                updateQuery.append(" SET New_Status=?, New_Modified_Time=? ");
                updateQuery
                        .append(" WHERE Subscription_ID= ? AND (old_status='ACTIVE' OR old_status='PENDING_ACTIVATION') ");
                updateQuery
                        .append(" AND (New_Status IS NULL OR New_Status='') ORDER BY Old_Modified_Time DESC LIMIT 1 ");
                stmt1 = reportDBConn.prepareStatement(updateQuery.toString());
                stmt1.setString(1, subscriptionStatus);
                stmt1.setDate(2, new java.sql.Date(lastModifyDate.getTime()));
                stmt1.setLong(3, subscriptionId);
                stmt1.executeUpdate();
            } else if (SubscriptionStatus.ACTIVE.name().equals(
                    subscriptionStatus)) {

                // Update Pending record
                StringBuilder updateQuery = new StringBuilder(
                        " UPDATE status_transition_view ");
                updateQuery.append(" SET New_Status=?, New_Modified_Time=? ");
                updateQuery
                        .append(" WHERE Subscription_ID= ? AND old_status=? ");
                updateQuery
                        .append(" AND (New_Status IS NULL OR New_Status='') ORDER BY Old_Modified_Time DESC LIMIT 1 ");
                stmt1 = reportDBConn.prepareStatement(updateQuery.toString());
                stmt1.setString(1, SubscriptionStatus.ACTIVE.name());
                stmt1.setDate(2, new java.sql.Date(lastModifyDate.getTime()));
                stmt1.setLong(3, subscriptionId);
                stmt1.setString(4, SubscriptionStatus.PENDING_ACTIVATION.name());
                stmt1.executeUpdate();

                // Insert new Active Record if not exist
                stmt2 = reportDBConn
                        .prepareStatement("SELECT Subscription_ID FROM status_transition_view WHERE Subscription_ID=? AND Old_Status=? AND (New_Status IS NULL OR New_Status='')");
                stmt2.setLong(1, subscriptionId);
                stmt2.setString(2, SubscriptionStatus.ACTIVE.name());
                rs1 = stmt2.executeQuery();
                if (rs1 == null || !rs1.next()) {

                    StringBuilder insertQuery = new StringBuilder(
                            " INSERT INTO status_transition_view");
                    insertQuery
                            .append("(Subscription_ID, Old_Status, Old_Modified_Time) VALUES(?, ?, ?)");
                    stmt3 = reportDBConn.prepareStatement(insertQuery
                            .toString());
                    stmt3.setLong(1, subscriptionId);
                    stmt3.setString(2, SubscriptionStatus.ACTIVE.name());
                    stmt3.setDate(3,
                            new java.sql.Date(lastModifyDate.getTime()));
                    stmt3.executeUpdate();
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
                if (stmt2 != null)
                    stmt2.close();
                if (stmt3 != null)
                    stmt3.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return weekNumber;
    }

    public static Integer addCampaignScheduleAlert(Long subscriptionId,
            java.util.Date activationDate, Integer activeWeekNumber,
            int messagesPerWeek, java.util.Date endDate,
            Connection reportDBConn, Integer startActiveMod) {
        if (endDate == null) {
            LOGGER.error("endDate null VALUE FOR subscriptionId:"
                    + subscriptionId);
        }
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(endDate);

        Calendar activationCal = Calendar.getInstance();
        activationCal.setTime(activationDate);

        // Fetch existing schedules for subscription;
        Set<String> scheduleSet = ReportingHelper
                .getRecordFromCampaignScheduleAlert(subscriptionId,
                        reportDBConn);

        // check if activation starts from mid week i.e Active-start%7==4
        if (startActiveMod == 4 && messagesPerWeek == 2) {
            int count = 0;
            while (activationCal.before(currentCal)
                    || activationCal.equals(currentCal)) {
                if (count > 0) {
                    insertRecordInCampaignScheduleAlert(subscriptionId,
                            activationCal,
                            String.format("w%d_1", activeWeekNumber),
                            reportDBConn, scheduleSet);
                } else {
                    // Insert first week message irrespective of week number
                    insertRecordInCampaignScheduleAlert(subscriptionId,
                            activationCal, String.format("w%d_1", 1),
                            reportDBConn, scheduleSet);
                    activationCal.add(Calendar.DATE, 3);// Add 3 days
                    activeWeekNumber++;// Point to next week
                    count++;
                    continue;
                }
                // for second message
                if (messagesPerWeek == 2) {
                    Calendar secondMessageDate = Calendar.getInstance();
                    secondMessageDate.setTime(activationCal.getTime());
                    secondMessageDate.add(Calendar.DATE, 4);// Add 4 days
                    String weekId = String.format("w%d_2", activeWeekNumber);
                    insertRecordInCampaignScheduleAlert(subscriptionId,
                            secondMessageDate, weekId, reportDBConn,
                            scheduleSet);
                }
                activationCal.add(Calendar.DATE, 7);// Add 7 days
                activeWeekNumber++;
            }
        }
        // check if activation starts from as per start date day i.e
        // Active-start%7=0
        else if (startActiveMod == 0) {
            int count = 0;
            while (activationCal.before(currentCal)
                    || activationCal.equals(currentCal)) {
                if (count > 0) {
                    insertRecordInCampaignScheduleAlert(subscriptionId,
                            activationCal,
                            String.format("w%d_1", activeWeekNumber),
                            reportDBConn, scheduleSet);
                } else {
                    // Insert first week message irrespective of week number
                    insertRecordInCampaignScheduleAlert(subscriptionId,
                            activationCal, String.format("w%d_1", 1),
                            reportDBConn, scheduleSet);
                    count++;
                }
                // for second message
                if (messagesPerWeek == 2) {
                    Calendar secondMessageDate = Calendar.getInstance();
                    secondMessageDate.setTime(activationCal.getTime());
                    secondMessageDate.add(Calendar.DATE, 4);// Add 4 days
                    String weekId = String.format("w%d_2", activeWeekNumber);
                    insertRecordInCampaignScheduleAlert(subscriptionId,
                            secondMessageDate, weekId, reportDBConn,
                            scheduleSet);
                }
                activationCal.add(Calendar.DATE, 7);// Add 7 days
                activeWeekNumber++;
            }
        }
        else {
            LOGGER.error("addCampaignScheduleAlert Invalid activation date for subscription:"
                    + subscriptionId);
        }
        return 0;
    }

    /**
     * insert Record In Campaign Schedule Alert
     * 
     * @param subscriptionId
     * @param date
     * @param weekId
     * @param reportDBConn
     */
    private static void insertRecordInCampaignScheduleAlert(
            Long subscriptionId, Calendar date, String weekId,
            Connection reportDBConn, Set<String> scheduleSet) {
        PreparedStatement stmt = null;
        Integer dateId = getDateId(date.getTime(), reportDBConn);
        Long campaignId = getCampaignId(weekId, "", 0, reportDBConn);

        String key = subscriptionId + "_" + campaignId + "_" + dateId;
        if (!scheduleSet.contains(key)) {
            try {
                StringBuilder insertQuery = new StringBuilder(
                        " INSERT INTO campaign_schedule_alerts");
                insertQuery
                        .append("(Subscription_ID, Campaign_ID, Date_ID, Time_ID) VALUES(?, ?, ?, 0)");
                stmt = reportDBConn.prepareStatement(insertQuery.toString());
                stmt.setLong(1, subscriptionId);
                stmt.setLong(2, campaignId);
                stmt.setInt(3, dateId);
                stmt.executeUpdate();
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                LOGGER.info(e.getMessage());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Insert new record in table subscription_status_measure
     * 
     * @param subscriptionId
     * @param channelId
     * @param operatorId
     * @param subscriptionPackId
     * @param subscriptionStatus
     * @param weekNumber
     * @param lastModifiedTime
     * @param reportDBConn
     */
    private static void insertRecordInSubscriptionStatusMeasure(
            long subscriptionId, long channelId, long operatorId,
            long subscriptionPackId, String subscriptionStatus,
            Long weekNumber, java.util.Date lastModifiedTime,
            Connection reportDBConn) {
        PreparedStatement stmt = null;
        try {
            Integer dateId = getDateId(lastModifiedTime, reportDBConn);
            Integer timeId = getTimeId(lastModifiedTime, reportDBConn);

            StringBuilder insertQuery = new StringBuilder(
                    " INSERT INTO subscription_status_measure");
            insertQuery
                    .append("(Subscription_ID, Date_ID, Time_ID , Operator_ID,");
            insertQuery
                    .append(" Status, Last_Modified_Time) ");
            insertQuery.append(" VALUES(?, ?, ?, ?, ?, ?) ");
            stmt = reportDBConn.prepareStatement(insertQuery.toString());
            stmt.setLong(1, subscriptionId);
            stmt.setInt(2, dateId);
            stmt.setInt(3, timeId);
            stmt.setLong(4, operatorId);
            stmt.setString(5, subscriptionStatus);
            if (lastModifiedTime != null) {
                stmt.setDate(6, new java.sql.Date(lastModifiedTime.getTime()));
            } else {
                stmt.setDate(6, null);
            }
            stmt.executeUpdate();
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private static void insertRecordInStatusTransitionView(long subscriptionId,
            String oldStatus, String newStatus, java.util.Date oldModifiedTime,
            java.util.Date newModifiedTime, Connection reportDBConn) {
        PreparedStatement stmt = null;
        try {
            StringBuilder insertQuery = new StringBuilder(
                    " INSERT INTO status_transition_view");
            insertQuery
                    .append("(Subscription_ID, Old_Status, New_Status, Old_Modified_Time, New_Modified_Time) ");
            insertQuery.append(" VALUES(?, ?, ?, ?, ?) ");
            stmt = reportDBConn.prepareStatement(insertQuery.toString());
            stmt.setLong(1, subscriptionId);
            stmt.setString(2, oldStatus);
            stmt.setString(3, newStatus);
            stmt.setDate(4, new java.sql.Date(oldModifiedTime.getTime()));
            if (newModifiedTime != null) {
                stmt.setDate(5, new java.sql.Date(newModifiedTime.getTime()));
            } else {
                stmt.setDate(5, null);
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * This method return Mobile Academy Content ID & percentage Listened from table ma_dimension.
     */
    public static Map<String, Object> getMAContent(String contentName,
            String fileName,
            String type, Connection reportDBConn,Integer recordDuration) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Long contentId = null;
        Integer contentDuration = null;
        Map<String, Object> contentMap = new HashMap<String, Object>();
        Integer percentageListened = null;
        try {

            stmt = reportDBConn
                    .prepareStatement("SELECT id,duration FROM ma_dimension WHERE content_name=? AND file_name=?");
            stmt.setString(1, contentName);
            stmt.setString(2, fileName);
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                contentId = rs.getLong(1);
                contentDuration = rs.getInt(2);
            } else {
                LOGGER.warn("Getting ContentName:" + contentName
                        + ", fileName:" + fileName
                        + "--From Motech Which Not exist in MA dimension");
            }
            // calculate percentage listened
            if (contentDuration != null && recordDuration != null) {
                percentageListened = ((recordDuration * 100) / contentDuration);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        contentMap.put("contentId", contentId);
        contentMap.put("percentageListened", percentageListened);
        return contentMap;
    }

    private static Set<String> getRecordFromCampaignScheduleAlert(
            Long subscriptionId, Connection reportDBConn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Set<String> scheduleSet = new HashSet<String>();
        try {
            stmt = reportDBConn
                    .prepareStatement("Select Subscription_ID,Campaign_ID,Date_ID from campaign_schedule_alerts Where Subscription_ID =?");
            stmt.setLong(1, subscriptionId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                scheduleSet.add(rs.getLong(1) + "_" + rs.getLong(2) + "_"
                        + rs.getLong(3));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return scheduleSet;
    }

    /**
     * This method return Mobile Kunji Content ID & Percentage Listened from
     * table mk_dimension.
     */
    public static Map<String, Object> getMKContent(String contentName,
            String fileName, String mobileKunjiCardCode,
            Connection reportDBConn, Integer recordDuration) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Long contentId = null;
        Integer contentDuration = null;
        Map<String, Object> contentMap = new HashMap<String, Object>();
        Integer percentageListened = null;
        try {

            stmt = reportDBConn
                    .prepareStatement("SELECT id,duration FROM mk_dimension WHERE file_name=? AND card_number=?");
            stmt.setString(1, fileName);
            stmt.setString(2, mobileKunjiCardCode);
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                contentId = rs.getLong(1);
                contentDuration = rs.getInt(2);
            } else {
                LOGGER.warn("Getting fileName:" + fileName
 + ", card_number:"
                        + mobileKunjiCardCode
                        + "--From Motech Which Not exist in mk_dimension");
            }
            // calculate percentage listened
            if (contentDuration != null && recordDuration != null) {
                percentageListened = ((recordDuration * 100) / contentDuration);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        contentMap.put("contentId", contentId);
        contentMap.put("percentageListened", percentageListened);
        return contentMap;
    }

    private static java.util.Date getDateWithoutTime(java.util.Date inputDate) {
        if (inputDate == null) {
            return null;
        }
        java.util.Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.util.Date(cal.getTime().getTime());
    }

    public static boolean setCourseStatus(Long flwId, String contentName,
            java.util.Date startTime, java.util.Date endTime,
            Boolean correctAnswerEntered, String fileName,
            Connection reportDBConn) {
        PreparedStatement stmt1 = null;
        ResultSet rs1 = null;
        PreparedStatement stmt2 = null;
        try {
            if ("Chapter01_Lesson01".equals(contentName)
                    && "ch1_l1.wav".equals(fileName)) {
            stmt1 = reportDBConn
                        .prepareStatement("SELECT id, end_time FROM ma_course_status WHERE flw_id =? ORDER BY id desc LIMIT 1");
            stmt1.setLong(1, flwId);
            rs1 = stmt1.executeQuery();
            if (rs1 != null && rs1.next()) {
                    java.sql.Date courseEndTime = rs1.getDate(2);
                    if (courseEndTime != null) {
                        // insert new record
                        StringBuilder insertQuery = new StringBuilder(
                                " INSERT INTO ma_course_status");
                        insertQuery
                                .append("(flw_id, start_time) ");
                        insertQuery.append(" VALUES(?, ?) ");
                        stmt2 = reportDBConn.prepareStatement(insertQuery
                                .toString());
                        stmt2.setLong(1, flwId);
                        stmt2.setTimestamp(2,
                                new java.sql.Timestamp(startTime.getTime()));
                        stmt2.executeUpdate();
                }
            } else {
                    // insert new record
                    StringBuilder insertQuery = new StringBuilder(
                            " INSERT INTO ma_course_status");
                    insertQuery.append("(flw_id, start_time) ");
                    insertQuery.append(" VALUES(?, ?) ");
                    stmt2 = reportDBConn.prepareStatement(insertQuery
                            .toString());
                    stmt2.setLong(1, flwId);
                    stmt2.setTimestamp(2,
                            new java.sql.Timestamp(startTime.getTime()));
                    stmt2.executeUpdate();
            }

            } else if ("Chapter11_Question04".equals(contentName)
                    && correctAnswerEntered != null
                    && "ch11_q4.wav".equals(fileName)) {
                stmt1 = reportDBConn
            .prepareStatement("SELECT MAX(id) FROM ma_course_status WHERE flw_id =?");
                stmt1.setLong(1, flwId);
                rs1 = stmt1.executeQuery();
                if (rs1 != null && rs1.next()) {
                // update
                StringBuilder insertQuery = new StringBuilder(
                        " UPDATE ma_course_status");
                insertQuery.append(" SET end_time=?");
                insertQuery
                        .append(" WHERE id=?");
                stmt2 = reportDBConn.prepareStatement(insertQuery.toString());
                    stmt2.setTimestamp(1,
                            new java.sql.Timestamp(endTime.getTime()));
                stmt2.setLong(2, rs1.getLong(1));
                stmt2.executeUpdate();
                }
        }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs1 != null)
                    rs1.close();
                if (stmt1 != null)
                    stmt1.close();
                if (stmt2 != null)
                    stmt2.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return true;
    }

    public static Integer calculateChapterScore(Long id,
            String startTime, String chapterName, Long flwId,String contentFile,
            Connection reportDBConn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer score = null;
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT SUM( CASE WHEN main.correct_answer_entered=1 THEN '1' ELSE '0' END) AS 'score' FROM(");
            sqlQuery.append(" SELECT * FROM (SELECT cc.id,md.content_Name,cd.flw_id,cc.start_time,correct_answer_entered ");
            sqlQuery.append(" FROM ma_call_content_measure cc");
            sqlQuery.append(" INNER JOIN ma_dimension md ON cc.content_id =md.id");
            sqlQuery.append(" INNER JOIN ma_call_detail_measure cd ON cd.id=cc.call_detail_id");
            sqlQuery.append(" Where cc.start_time<='" + startTime + "'");
            sqlQuery.append(" AND md.content_Name like '" + chapterName
                    + "_Question%%' ");
            sqlQuery.append(" AND md.file_name like '" + contentFile
                    + "_q%%.wav' ");
            sqlQuery.append(" AND cc.correct_answer_entered IS NOT NULL AND cd.flw_id =?");
            sqlQuery.append(" order by cc.start_time DESC) gp GROUP BY gp.content_name) main GROUP BY main.flw_id");
            stmt = reportDBConn
                    .prepareStatement(sqlQuery.toString());
            stmt.setLong(1, flwId);
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                score = rs.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return score;
    }

    public static Long updateCourseSMSContent(Long flwId, String smsRef,
            Boolean sentFlag, java.util.Date modificationDate,
            Connection reportDBConn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        ResultSet rs2 = null;
        Long recordId = null;
        try {
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT id FROM ma_course_status ");
            sqlQuery.append(" WHERE flw_id =? AND complete_time IS NOT NULL ORDER BY start_time DESC LIMIT 1 ");
            stmt = reportDBConn.prepareStatement(sqlQuery.toString());
            stmt.setLong(1, flwId);
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                recordId = rs.getLong(1);
                StringBuilder insertQuery = new StringBuilder(
                        " UPDATE ma_course_status");
                insertQuery
                        .append(" SET sms_reference_number=?, sent_notification=? ,completionModificationDate=?");
                insertQuery.append(" WHERE id=?");
                stmt2 = reportDBConn.prepareStatement(insertQuery.toString());
                stmt2.setString(1, smsRef);
                stmt2.setBoolean(2, sentFlag);
                stmt2.setTimestamp(3,
                        new java.sql.Timestamp(modificationDate.getTime()));
                stmt2.setLong(4, recordId);
                stmt2.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (rs2 != null)
                    rs2.close();
                if (stmt2 != null)
                    stmt2.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return 0l;
    }


    public static Long updateCourseTime(Long flwId, String creationDate,
            java.util.Date completeDate, java.util.Date modificationDate,
            Connection reportDBConn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        ResultSet rs2 = null;
        Long recordId = null;
        try {
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT id FROM ma_course_status");
            sqlQuery.append(" WHERE flw_id=? AND start_time<'" + creationDate
                    + "' ");
            sqlQuery.append(" AND complete_time IS NULL ORDER BY start_time DESC LIMIT 1 ");
            stmt = reportDBConn.prepareStatement(sqlQuery.toString());
            stmt.setLong(1, flwId);
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                recordId = rs.getLong(1);
                StringBuilder insertQuery = new StringBuilder(
                        " UPDATE ma_course_status");
                insertQuery
                        .append(" SET complete_time=? , activityModificationDate=?");
                insertQuery.append(" WHERE id=?");
                stmt2 = reportDBConn.prepareStatement(insertQuery.toString());
                stmt2.setTimestamp(1,
                        new java.sql.Timestamp(completeDate.getTime()));
                stmt2.setTimestamp(2,
                        new java.sql.Timestamp(modificationDate.getTime()));
                stmt2.setLong(3, recordId);
                stmt2.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (rs2 != null)
                    rs2.close();
                if (stmt2 != null)
                    stmt2.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return 0l;
    }
    
	public static Long beneficiary(String mcts_id, String rch_id, Byte mother_beneficiary,
    		Connection reportDBConn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Long beneficiary_id = null;
        try {
        	if(mcts_id != null && rch_id ==null) {
	            StringBuilder sqlQuery = new StringBuilder(
	                    "SELECT id FROM Beneficiary");
	            sqlQuery.append(" WHERE mcts_id='"+ mcts_id +"' AND mother_beneficiary=" + mother_beneficiary
	                    + " ");
	            stmt = reportDBConn.prepareStatement(sqlQuery.toString());
	            rs = stmt.executeQuery();
	            if (rs != null && rs.next()) {
	                beneficiary_id = rs.getLong(1);  
            }} else if(mcts_id == null && rch_id != null) {
            	StringBuilder sqlQuery = new StringBuilder(
                        "SELECT id FROM Beneficiary");
                sqlQuery.append(" WHERE rch_id='"+ rch_id +"' AND mother_beneficiary=" + mother_beneficiary
                        + " ");
                stmt = reportDBConn.prepareStatement(sqlQuery.toString());
                rs = stmt.executeQuery();
                if (rs != null && rs.next()) {
                    beneficiary_id = rs.getLong(1);
            }
                } else {
                	StringBuilder sqlQuery = new StringBuilder(
                        "SELECT id FROM Beneficiary");
                sqlQuery.append(" WHERE rch_id='"+ rch_id +"' AND mcts_id='"+ mcts_id +"' AND mother_beneficiary=" + mother_beneficiary
                        + " ");
                stmt = reportDBConn.prepareStatement(sqlQuery.toString());
                rs = stmt.executeQuery();
                if (rs != null && rs.next()) {
                    beneficiary_id = rs.getLong(1);
            }
           }
	            }
        	catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
    }
        return beneficiary_id;
  }
	
	public static Integer operator(String operatorId,
    		Connection reportDBConn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer operator = null;
        try {
        	StringBuilder sqlQuery = new StringBuilder(
                    "SELECT id FROM dim_operator");
            sqlQuery.append(" WHERE operator_code='"+ operatorId + "' ");
            stmt = reportDBConn.prepareStatement(sqlQuery.toString());
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                operator = rs.getInt(1);
            }
        } catch (Exception e){
        	LOGGER.error(e.getMessage(), e);
        }
		return operator;
	}
}
