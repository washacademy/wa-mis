%~d0
cd %~dp0
java -Dtalend.component.manager.m2.repository="%cd%/../lib" -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/crypto-utils.jar;../lib/dom4j-1.6.1.jar;../lib/log4j-1.2.17.jar;../lib/mysql-connector-java-5.1.30-bin.jar;../lib/talend_file_enhanced_20070724.jar;../lib/talendcsv.jar;update_location_0_1.jar; local_wash_new.update_location_0_1.update_location  --context=Default %*