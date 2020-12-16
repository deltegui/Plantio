echo 'Going to build client'
cd ./client
echo 'Installing dependecies...'
npm i
echo 'Building...'
npm run build
echo 'Creating public folder and passing built client'
mkdir ../plantio/src/main/resources/public
cp -a ./dist/* ../plantio/src/main/resources/public/
echo 'Building server...'
cd ../plantio/
mvn clean install spring-boot:repackage
cp -a ./target/plantio-0.0.1-SNAPSHOT.jar ../plantio.jar

