#!/bin/bash

MD_FILE=$1

if [ -z "$MD_FILE" ]; then
  echo "Usage: $0 <markdown-file>"
  exit 1
fi

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
REPO_ROOT=$(dirname "$SCRIPT_DIR")
PDF_DIR="$REPO_ROOT/Slide"
HTML_DIR="$REPO_ROOT/Slide"
THEME_CSS="$SCRIPT_DIR/nlh-theme.css"

mkdir -p "$PDF_DIR"

BASE_NAME=$(basename "${MD_FILE%.*}")
PDF_OUT="$PDF_DIR/$BASE_NAME.pdf"

marp "$MD_FILE" --allow-local-files --theme "$THEME_CSS" --pdf --output "$PDF_OUT"

