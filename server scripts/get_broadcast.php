<?php
 
/*
 * Following code will list all the requests
 */
 



 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$response = array();

 $tag = $_POST['tag'];
 $query = $_POST['query'];

 if ($tag=="1") {
   
	$query = (int)$query;
	$query=20*$query;
// get all request from table
$result = mysql_query("SELECT * FROM UserRequests ORDER BY reqID DESC LIMIT 0 , $query")or die(mysql_error()) ;

 }
 
 else if($tag=="2"){
     
     
    // search from UserRequests table
    $result = mysql_query("SELECT * FROM UserRequests WHERE item LIKE '%$query%' OR location LIKE '%$query%' ORDER BY reqID DESC")or die(mysql_error()) ;

	}
	
// check for empty result

if (mysql_num_rows($result) > 0) {
    // looping through all results
    // requests node
    $response["UserRequests"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
           $UserRequest = array();
		$UserRequest["reqID"] = $row["reqID"];
        $UserRequest["email"] = $row["email"];
        $UserRequest["item"] = $row["item"];
        $UserRequest["location"] = $row["location"];
		$UserRequest["exptime"] = $row["exptime"];
		$UserRequest["expdate"] = $row["expdate"];
		$UserRequest["description"] = $row["description"];
 
        // push single request into final response array
        array_push( $response["UserRequests"], $UserRequest);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} 

else {
    // no requests found
    $response["success"] = 0;
    $response["message"] = "No requests found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
