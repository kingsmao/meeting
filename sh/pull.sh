#!/usr/bin/expect -f
spawn sh gitpull.sh
expect eof
spawn sh restart.sh
expect eof
interact
