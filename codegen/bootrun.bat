call jset.bat
cd ..
cd ./security
start gradle bootrun
cd ../apigateway
start gradle bootrun
cd ../account
start gradle bootrun
cd ../service-center
start gradle bootrun
