
ifeq ($(JAVACALL_DIR),)
$(error JAVACALL_DIR is not set.)
endif

BUILDTOOLS_BIN_DIR := $(TOOLCHAIN_HOME_DIR)/bin
CC=$(BUILDTOOLS_BIN_DIR)/xtensa-esp32-elf-gcc
AR=$(BUILDTOOLS_BIN_DIR)/xtensa-esp32-elf-ar

CFLAGS += 
CFLAGS += $(SPECIFIC_DEFINITIONS)

include environment.gmk

#For each project specified toolchain shall be selected
include $(JAVACALL_DIR)/build/gcc/rules.gmk

#Specific targets can be achieved before and after achievement of common targets
#Specify these targets after including 'rules.gmk' file.
pre_target:
#post_target: $(JAVACALL_OUTPUT_LIB_DIR)/emulator.res
