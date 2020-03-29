curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"data":{"attributes":{"from":"4257669358","body":"47.75,-122.33" }}}' \
  http://localhost:9090/sms/callback
