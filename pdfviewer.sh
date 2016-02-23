	# Convert the eiPDFViewer into an executable jar.
# Designed to be filesystem agnostic so that whatever
# machine it's on, this script will run fine so long as
# it isn't moved from its current location.

# Set the base directory, the current location of this script
BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "$BASE_DIR" #remove this

# Get the directory paths for the operation
OUT_DIR="$BASE_DIR/out/production/PDFViewer/"
LIB_DIR="$BASE_DIR/lib"
SRC_DIR="$BASE_DIR/src"
SRC_RESOURCES_DIR="$BASE_DIR/resources"

PCKG_PATH="com/pilotfish/eip/modules/pdfviewer"

TARGET_DIR="$BASE_DIR/jar"
TARGET_CLASS_CONTROLLER_DIR="$TARGET_DIR/$PCKG_PATH/controller"
TARGET_CLASS_MODEL_DIR="$TARGET_DIR/$PCKG_PATH/model"
TARGET_CLASS_UTIL_DIR="$TARGET_DIR/$PCKG_PATH/util"
TARGET_CLASS_VIEW_DIR="$TARGET_DIR/$PCKG_PATH/view"

TARGET_SRC_DIR="$TARGET_DIR/source"
TARGET_SRC_CONTROLLER_DIR="$TARGET_SRC_DIR/$PCKG_PATH/controller"
TARGET_SRC_MODEL_DIR="$TARGET_SRC_DIR/$PCKG_PATH/model"
TARGET_SRC_UTIL_DIR="$TARGET_SRC_DIR/$PCKG_PATH/util"
TARGET_SRC_VIEW_DIR="$TARGET_SRC_DIR/$PCKG_PATH/view"

TARGET_LIB_DIR="$TARGET_DIR/lib"

if [ -d $TARGET_DIR ]
	then
		echo "--Removing old files"
		rm -rf "$TARGET_DIR"
fi

# Clean directory of mac files
echo "--Clearing mac .DS_Store files from code directory"
find "$BASE_DIR" -name ".DS_Store" -exec rm -rf {} \;

# Create target directories
echo "--Creating target directories"
# Create target root directory
mkdir -p "$TARGET_DIR"
# # Create target class directories
mkdir -p "$TARGET_CLASS_CONTROLLER_DIR"
mkdir -p "$TARGET_CLASS_MODEL_DIR"
mkdir -p "$TARGET_CLASS_UTIL_DIR"
mkdir -p "$TARGET_CLASS_VIEW_DIR"
# # Create target source root directory
# mkdir -p "$TARGET_SRC_DIR"
# # Create target source directories
mkdir -p "$TARGET_SRC_CONTROLLER_DIR"
mkdir -p "$TARGET_SRC_MODEL_DIR"
mkdir -p "$TARGET_SRC_UTIL_DIR"
mkdir -p "$TARGET_SRC_VIEW_DIR"
# Create target lib directory
mkdir "$TARGET_LIB_DIR"

# # Copy class files
# echo "--Copying class files"
cp -R "$OUT_DIR" "$TARGET_DIR"

# # Copy source files
# echo "--Copying source files"
cp -R "$SRC_DIR/" "$TARGET_SRC_DIR"
cp -R "$SRC_RESOURCES_DIR" "$TARGET_SRC_DIR"

# # Copy lib files
echo "--Copying lib files"
cp -R "$LIB_DIR/" "$TARGET_DIR"

cd $TARGET_DIR

MANIFEST_TEXT="Main-Class: com/pilotfish/eip/modules/pdfviewer/Main\nClass-Path: pdfbox-1.8.10.jar"
echo -e $MANIFEST_TEXT > manifest.txt

echo "--Creating PDFViewer Jar"
jar cfm pdfviewer.jar manifest.txt .