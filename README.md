# Streaming  

Primeri sporočil in zahtevkov, ki jih bo obdelovala aplikacija:  
	??  
	
Kompleksnješi zahtevki (pri obdelavi sodelujejo vsaj tri mikrostoritve):  
	??


+ update Architecture.png

run docker container:  
    docker run -dit --name catalogs -p 8081:8081 mu2729/streaming-catalogs


Konfiguracija:  
 - konfiguracijske datoteke:  
   + api/src/main/java/config.yml  
 - okoljske spremenljivke:  
    override environment, ime microservica, verzijo, base-url in port serverja, datasource connection url, username, password, max-pool-size  
 - konfiguracijski strežnik: spreminjanje konfiguracije, ki se jo lahko določi z okoljskimi spr.