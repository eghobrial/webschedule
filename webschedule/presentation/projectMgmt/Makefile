#
#  webschedule
#  Enhydra Application Makefile
#
#

#
#  Where is the root of the source tree?
#
ROOT =	../../..

#
#  What package does this directory represent? (use / instead of .)
#
PACKAGEDIR = webschedule/presentation/projectMgmt

#
# To add new directories, create the directories and add their names
# this list. Be sure to copy a Makefile into each directory. Then add a
# "../" to the ROOT variable, and add the directory name to the end of.
# the PACKAGEDIR variable.
#

#
# To add more Java classes, create the .java files and add their names to
# this list (without the .java extension).
#
CLASSES = \
    Counter 	\
    ProjCatalog  \
    Edit          \
    Project_Admin   \
    Proj_passwd      \
    Proj_passwd_cat	\
    proj_bill_list	\
    proj_annual_list	\
    proj_FY_list	\
    User_proj	\
    credit_pilot_hours	\
    debt_pilot_hours   \
    projadminfunc \
    nonucsdprojlist\
        ucsdprojlist \
	ucsdprojectsedit \
	nonucsdprojectsedit \
	paycodes\
	modlog
   
	
HTML_DIR = ../../resources/projectMgmt

HTML_CLASSES = 		\
	ProjCatalogHTML      \
	ProjCatalogScriptHTML   \
	EditHTML 		\
	Project_AdminHTML	\
	Project_AdminScriptHTML   \
	Proj_passwdHTML   \
        Proj_passwd_catHTML \
	proj_bill_listHTML	\
	proj_annual_listHTML	\
	proj_FY_listHTML	\
	User_projHTML	\
	credit_pilot_hoursHTML   \
	debt_pilot_hoursHTML  \
        projadminfuncHTML \
	nonucsdprojlistHTML\
	ucsdprojlistHTML \
	ucsdprojectseditHTML \
        nonucsdprojectseditHTML \
	paycodesHTML\
	modlogHTML

# The "?Edit.po" dummy query string is used so the URL mapping only maps this
# sepcific URL. "Edit.po" is an indicator to an HTML code reader of 
# what URL is DiscCatalog.html is mapped to.
#XMLC_Edit_OPTS = -urlmapping 'DiscCatalog.html?Edit.po' 'Edit.po'

XMLC_HTML_OPTS_FILE = options.xmlc

include $(ROOT)/config.mk
