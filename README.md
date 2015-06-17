# hetzner-status-filter
Filter for the Atom feed of hetzner-status.de

## Build
Maven: `mvn clean package`

## Usage
Start the app with `java -jar target/*.jar`

Add `http://<yourhost>:8080/{de,en}.atom?filter=<some,urlencoded+terms>` to your feedreader
 
## Examples
Fetch the german feed and filter all the vServer related entries: `http://localhost:8080/de.atom?filter=vhost`

Fetch the english feed and filter everything about shared hosting, managed server, backupservers and sql databases:
`http://localhost:8080/en.atom?filter=shared+hosting,managed+server,backupserver,sql`
