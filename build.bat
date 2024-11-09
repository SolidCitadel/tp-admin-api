echo Jar Build
call gradlew build

echo Image Build
docker build -t solidcitadel/transitplanner-manager:1.0 .

echo Image Push
docker push solidcitadel/transitplanner-manager:1.0