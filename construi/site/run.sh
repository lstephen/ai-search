#!/bin/bash

[[ -n "$GIT_AUTHOR_NAME" ]] && git config user.name $GIT_AUTHOR_NAME
[[ -n "$GIT_AUTHOR_EMAIL" ]] && git config user.email $GIT_AUTHOR_EMAIL

mvn -B clean site

