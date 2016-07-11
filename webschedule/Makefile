#
#  webschedule
#  Enhydra Application Makefile
#
#

#
#  Where is the root of the source tree?
#
ROOT =	..

#
#  What package does this directory represent? (use / instead of .)
#
PACKAGEDIR = webschedule

#
# To add new directories, create the directories and add their names
# this list. Be sure to copy a Makefile into each directory. Then add a
# "../" to the ROOT variable, and add the directory name to the end of.
# the PACKAGEDIR variable.
#
SUBDIRS = \
	data \
	business \
	presentation 
	

#
# To add more Java classes, create the .java files and add their names to
# this list (without the .java extension).
#
CLASSES = \
	Webschedule \
	WebscheduleException \
	SendMail \
	SendMailLL\
	DOMTableRow \
	UCProjectSort \
	NONUCProjectSort

MISC_BUILD = ${OUTPUT}/webschedule.conf ${OUTPUT}/multiserver.conf ${OUTPUT}/start

include $(ROOT)/config.mk

${OUTPUT}/webschedule.conf: webschedule.conf.in
	@[ -d ${OUTPUT} ] || mkdir -p ${OUTPUT}
	rm -rf ${OUTPUT}/webschedule.conf
	sed -e s%@OUTPUT@%`cd $(OUTPUT);pwd`%g \
	    < webschedule.conf.in > ${OUTPUT}/webschedule.conf

${OUTPUT}/multiserver.conf: multiserver.conf.in
	@[ -d ${OUTPUT} ] || mkdir -p ${OUTPUT}
	rm -rf ${OUTPUT}/multiserver.conf
	sed -e s%@OUTPUT@%`cd $(OUTPUT);pwd`%g \
	    < multiserver.conf.in > ${OUTPUT}/multiserver.conf

${OUTPUT}/start: start
	@[ -d ${OUTPUT} ] || mkdir -p ${OUTPUT}
	rm -f ${OUTPUT}/start
	sed -e s%@ENHYDRA_DIR@%`cd $(ENHYDRA_DIR);pwd`%g \
	    -e s%@OUTPUT@%`cd $(OUTPUT);pwd`%g \
	    < start > ${OUTPUT}/start
	chmod +x ${OUTPUT}/start

clean::
	rm -f ${OUTPUT}/webschedule.conf


