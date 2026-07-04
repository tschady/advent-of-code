#/bin/sh

security find-generic-password -s AOC_GITCRYPT_KEY_BASE64 -w | base64 -d > .git-crypt
git-crypt unlock .git-crypt
rm .git-crypt

