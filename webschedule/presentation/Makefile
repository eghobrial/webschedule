#
#  webschedule
#  Enhydra Application Makefile
#
#

#
#  Where is the root of the source tree?
#
ROOT =	../..

#
#  What package does this directory represent? (use / instead of .)
#
PACKAGEDIR = webschedule/presentation

#
# To add new directories, create the directories and add their names
# this list. Be sure to copy a Makefile into each directory. Then add a
# "../" to the ROOT variable, and add the directory name to the end of.
# the PACKAGEDIR variable.
#
SUBDIRS = \
	media			\
	assets			\
	personMgmt              \
	projectMgmt             \
	s_eventMgmt		\
	problemMgmt	        \
	complaintMgmt    \
	proposalMgmt  \
#	c_eventMgmt

#
# To add more Java classes, create the .java files and add their names to
# this list (without the .java extension).
#
#CLASSES = \
#	Redirect \
#	Welcome

CLASSES = ErrorHandler
	  	
	
HTML_DIR = ../resources

HTML_CLASSES = ErrorHTML

XMLC_HTML_OPTS_FILE = options.xmlc



#HTML_DIR = .
#HTML_CLASSES = WelcomeHTML

include $(ROOT)/config.mk

