<?php
// author : Gaurav kushwaha
            $url = 'https://api.parse.com/1/push';

            $appId = 'oS5B5on8xGqQFPDHvtR4jgmXsemYbdBq14DfWAzo';
            $restKey = '1AyXAJXC14zo291rkEOGZrptXyVi0pycfx28xeHs';

            $target_device = 'android';  // using object Id of target Installation.

            $push_payload = json_encode(array(
                    "where" => array(
                            "deviceType" => $target_device,
                    ),
                    "data" => array(
                            "alert" => "New user request added."
                    )
            ));

            $rest = curl_init();
            curl_setopt($rest,CURLOPT_URL,$url);
            curl_setopt($rest,CURLOPT_POST,1);
            curl_setopt($rest,CURLOPT_POSTFIELDS,$push_payload);
            curl_setopt($rest,CURLOPT_SSL_VERIFYPEER, false);  
			curl_setopt($rest,CURLOPT_RETURNTRANSFER, true);  
            curl_setopt($rest,CURLOPT_HTTPHEADER,
                    array("X-Parse-Application-Id: " . $appId,
                            "X-Parse-REST-API-Key: " . $restKey,
                            "Content-Type: application/json"));

            $response = curl_exec($rest);
             curl_close($rest);
            echo $response;

            ?>
