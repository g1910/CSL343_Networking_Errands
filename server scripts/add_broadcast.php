<?php
/*
 * Following code adds a new user broadcast to the database
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (  isset($_POST['item']) && isset($_POST['location'])) {
 
     $email = $_POST['email'];
    $item = $_POST['item'];
    $location = $_POST['location'];
    $description = $_POST['description'];
	$time=$_POST['time'];
	$date=$_POST['date'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    
    // mysql inserting a new row
    
	$addquery = mysql_query("INSERT INTO UserRequests( email,item,location,exptime,expdate,description) VALUES('$email','$item','$location','$time','$date', '$description' )");
    
    
 
    // check if row inserted or not
    if ($addquery) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "UserRequest successfully added.";
 
        // echoing JSON response
        
        include 'push.php';
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

