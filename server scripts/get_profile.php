<?php
 


 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$response = array();


 $id = $_POST['id'];
     

 $result = mysql_query("SELECT name,emailId,phone,address,picurl FROM User WHERE idUser='$id'")or die(mysql_error()) ;
 
 if (mysql_num_rows($result) > 0) {
    // looping through all results
    // requests node
    $response["Informations"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
           $Information = array();
	    $Information["name"] = $row["name"];
        $Information["email"] = $row["emailId"];
        $Information["phone"] = $row["phone"];
		$Information["address"] = $row["address"];
		$Information["picurl"] = $row["picurl"];
       
 
        // push single request into final response array
        array_push( $response["Informations"], $Information);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} 

else {
    // no users found
    $response["success"] = 0;
    $response["message"] = "User not found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>