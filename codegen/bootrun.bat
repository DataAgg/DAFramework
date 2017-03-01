call jset.bat
cd ..
cd ./security
start gradle bootrun
cd ../apigateway
start gradle bootrun
cd ../EAccount
start gradle bootrun
cd ../service-center
start gradle bootrun
