<?php
 
/*
 * Following code adds a new user to the database
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['pname']) && isset($_POST['email']) && isset($_POST['picurl']) ) {
 
    $name = $_POST['pname'];
    $email = $_POST['email'];
   $picurl = $_POST['picurl'];
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
	$addquery = mysql_query("INSERT INTO User(name, emailId, picurl) VALUES('$name', '$email', '$picurl')");
$res = mysql_query("SELECT idUser FROM User WHERE emailId='$email'")or die(mysql_error()) ;
 $id=mysql_fetch_array($res);
	$id=$id['idUser'];
    }
    
 
    // check if row inserted or not
    if ($addquery) {
        // successfully inserted into database
        echo $id;
    } else {
        // failed to insert row
 $res = mysql_query("SELECT idUser FROM User WHERE emailId='$email'")or die(mysql_error()) ;
 $id=mysql_fetch_array($res);
	$id=$id['idUser'];
        echo $id;
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>
		
