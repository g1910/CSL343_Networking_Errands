<?php
 
/*
 * Following code adds a new user to the database
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['pname']) && isset($_POST['email']) ) {
 
    $name = $_POST['pname'];
    $email = $_POST['email'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    // checking if user already exists
    $checkquery = mysql_query("SELECT * FROM User WHERE emailId='$email'");
    $num = mysql_num_rows($checkquery);

    // mysql inserting a new row
    if($num==0)
    {
	$addquery = mysql_query("INSERT INTO User(name, emailId) VALUES('$name', '$email')");
    }
    
 
    // check if row inserted or not
    if ($addquery) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully added.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "User already present. Not added again.";
 
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
