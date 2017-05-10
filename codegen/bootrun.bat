call jset.bat
::start gradle bootrun -p ../service-center
::sleep 15
start gradle bootrun -p ../api-gateway
start gradle bootrun -p ../core-service
