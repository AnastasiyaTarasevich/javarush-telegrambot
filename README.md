## Deployment
Deployment process as easy as possible:
Required software:
- terminal for running bash scripts (for Windows git bash for example)
- docker
- docker-compose

directories for environments:

/.env/jrtb.env

File contents

* BOT_USERNAME=< username value >

* BOT_TOKEN=< token value >

to deploy application, switch to needed branch and run bash script:

$ bash start.sh

(your environment variables will be ignored by .gitignore and not shared)

That's all.