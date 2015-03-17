<?php
/*
 * Following code adds a new user broadcast to the database
 */
 // Kunal Yadav
// array for JSON response
$response = array();
 
// check for required fields
if (  isset($_POST['item']) && isset($_POST['location'])&& isset($_POST['quantity'])) {
 
    
    $item = $_POST['item'];
    $location = $_POST['location'];
    $quantity = $_POST['quantity'];
    $exprice = $_POST['price'];
	$time=$_POST['time'];
	$date=$_POST['date'];
   /* $time_hour = $_POST['time_hour'];
    $time_minute = $_POST['time_minute'];
    $date_day = $_POST['date_day'];
    $date_day = $_POST['date_month'];
    $date_day = $_POST['date_year']; */
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    
    // mysql inserting a new row
    
	$addquery = mysql_query("INSERT INTO UserRequests( item,location,quantity,exprice,exptime,expdate) VALUES('$item','$location','$quantity','$exprice','$time','$date')");
    
    
 
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

