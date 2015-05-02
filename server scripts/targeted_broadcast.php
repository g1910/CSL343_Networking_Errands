<?php
/*
 * Following code adds a new user broadcast to the database
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (  isset($_POST['user']) && isset($_POST['message'])) {
 
$message=$_POST['message'];
$userId=$_POST['user'];
//echo $userId;
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    
    // mysql inserting a new row
//echo "SELECT `emailId` from User where `idUser`=$userId";
    
	$addquery = mysql_query("SELECT `emailId` from User where `idUser`=$userId");
    
    
 
    // check if row inserted or not
    if ($addquery) {

$row = mysql_fetch_array($addquery);
$email=$row['emailId'];
echo $email;


        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "UserRequest successfully added.";
 
        // echoing JSON response
    
$url = 'https://api.parse.com/1/push';

            $appId = 'oS5B5on8xGqQFPDHvtR4jgmXsemYbdBq14DfWAzo';
            $restKey = '1AyXAJXC14zo291rkEOGZrptXyVi0pycfx28xeHs';

            $target_device = 'android';  // using object Id of target Installation.

            $push_payload = json_encode(array(
                    "where" => array(
                            "deviceType" => $target_device,
                            "emailId" => $email,
                    ),
                    "data" => array(
                            "alert" => $message
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


  //      include 'push.php';
echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response


    echo json_encode($response);
}
?>		