
ROOT =		../..

PACKAGEDIR = 	webschedule/data

CLASSES = 	BlocktimeBDO BlocktimeDataStruct BlocktimeDO BlocktimeQuery C_eventBDO C_eventDataStruct C_eventDO C_eventQuery complaintBDO complaintDataStruct complaintDO complaintQuery DowntimeBDO DowntimeDataStruct DowntimeDO DowntimeQuery OperatesBDO OperatesDataStruct OperatesDO OperatesQuery OperatorBDO OperatorDataStruct OperatorDO OperatorQuery PersonBDO PersonDataStruct PersonDO PersonQuery ProblemBDO ProblemDataStruct ProblemDO ProblemQuery ProjectBDO ProjectDataStruct ProjectDO ProjectQuery proposalBDO proposalDataStruct proposalDO proposalQuery ReporttrackBDO ReporttrackDataStruct ReporttrackDO ReporttrackQuery R_eventBDO R_eventDataStruct R_eventDO R_eventQuery S_eventBDO S_eventDataStruct S_eventDO S_eventQuery

SUBDIRS = 	

include $(ROOT)/config.mk

ENHYDRA_CLASSPATH:=:${PS}${ENHYDRA_CLASSPATH}

delete_source:
	@rm BlocktimeBDO.java BlocktimeDataStruct.java BlocktimeDO.java BlocktimeQuery.java C_eventBDO.java C_eventDataStruct.java C_eventDO.java C_eventQuery.java complaintBDO.java complaintDataStruct.java complaintDO.java complaintQuery.java DowntimeBDO.java DowntimeDataStruct.java DowntimeDO.java DowntimeQuery.java OperatesBDO.java OperatesDataStruct.java OperatesDO.java OperatesQuery.java OperatorBDO.java OperatorDataStruct.java OperatorDO.java OperatorQuery.java PersonBDO.java PersonDataStruct.java PersonDO.java PersonQuery.java ProblemBDO.java ProblemDataStruct.java ProblemDO.java ProblemQuery.java ProjectBDO.java ProjectDataStruct.java ProjectDO.java ProjectQuery.java proposalBDO.java proposalDataStruct.java proposalDO.java proposalQuery.java ReporttrackBDO.java ReporttrackDataStruct.java ReporttrackDO.java ReporttrackQuery.java R_eventBDO.java R_eventDataStruct.java R_eventDO.java R_eventQuery.java S_eventBDO.java S_eventDataStruct.java S_eventDO.java S_eventQuery.java ; \
        subs="$(SUBDIRS)" ; \
        if [ -n "$$MAKE_PATH" ] ; then \
            PARENT_PATH="$$MAKE_PATH" ; \
        else  \
            PARENT_PATH="." ; \
        fi ; \
        export PARENT_PATH ; \
        for d in $$subs ; \
            do \
                ( cd $$d || exit 1 ; \
                MAKE_PATH="$$PARENT_PATH/$$d" ; \
                export MAKE_PATH ; \
                echo "<< Start  CLEANUP pass in: $$MAKE_PATH >>" >> /dev/null ; \
                $(MAKE) delete_source || exit 0 ; \
                echo "<< Finish CLEANUP pass in: $$MAKE_PATH >>" >> /dev/null ; \
                ) ; \
            done

