call jset.bat
start gradle bootrun -p ../security
start gradle bootrun -p ../apigateway
start gradle bootrun -p ../account
start gradle bootrun -p ../service-center
