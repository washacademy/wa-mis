%~d0
cd %~dp0
java -Dtalend.component.manager.m2.repository="%cd%/../lib" -Xms256M -Xmx1024M -Dfile.encoding=UTF-8 -cp .;../lib/routines.jar;../lib/advancedPersistentLookupLib-1.2.jar;../lib/commons-collections-3.2.2.jar;../lib/crypto-utils.jar;../lib/dom4j-1.6.1.jar;../lib/jboss-serialization.jar;../lib/log4j-1.2.17.jar;../lib/mysql-connector-java-5.1.30-bin.jar;../lib/talend_file_enhanced_20070724.jar;../lib/talendcsv.jar;../lib/trove.jar;reporting_etl_job_0_2.jar;state_circle_0_1.jar;dimenison_table_execution_0_1.jar;panchayat_0_1.jar;block_test_0_1.jar;log_etl_table_info_test_0_1.jar;district_0_1.jar;circle_test_0_1.jar;language_0_1.jar;deployed_services_0_1.jar;state_test_0_1.jar; local_wash_new.reporting_etl_job_0_2.Reporting_ETL_Job  --context=Default %*