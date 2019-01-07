# Streaming  

run docker container:  
    docker run -dit --name catalogs -p 8080:8080 mu2729/streaming-catalogs


Konfiguracija:  
 - konfiguracijske datoteke:  
   + api/src/main/java/config.yml  
 - okoljske spremenljivke:  
    override environment, ime microservica, verzijo, base-url in port serverja, datasource connection url, username, password, max-pool-size,... 
 - konfiguracijski strežnik: spreminjanje konfiguracije, ki se jo lahko določi z okoljskimi spr.  
    -> etcd (dostop npr. http://192.168.99.100:2379/v2/keys/environments/dev/services/microservice-catalogs/1.0.0/)