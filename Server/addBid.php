<?php
include 'config.php';

$idAuction=$_GET['idAuction'];
$id_user=$_GET['id_user'];
$location = $_GET['location'];
$desc = $_GET['desc'];
$order= json_decode($_GET['order']);

$con=mysqli_connect($IP,$user,$pass,$db);

//echo "INSERT INTO `Bid`( `location`, `idUser`) VALUES (\"$location\",$id_user)";
mysqli_query($con,"INSERT INTO `Bid`( `location`, `idUser`,`bid_description`) VALUES (\"$location\",$id_user,\"$desc\")")  or die("Error : ".mysqli_error($con));

//echo "Select `idBid` from  `Bid` where `location`=\"$location\"  and `idUser` = $id_user";
$result =mysqli_query($con,"Select `idBid` from  `Bid` where `location`=\"$location\"  and `idUser` = $id_user")  or die("Error1 : ".mysqli_error($con));

while ($row=mysqli_fetch_assoc($result))
{
	$idBid=$row['idBid'];
}

//echo "INSERT INTO `Placed`(`idBid`, `idAuction`, `status`) VALUES ($idBid,$idAuction,'P')";
mysqli_query($con,"INSERT INTO `Placed`(`idBid`, `idAuction`, `status`) VALUES ($idBid,$idAuction,'P')")  or die("Error2 : ".mysqli_error($con));
//echo json_encode($order);
for ($i=0;$i<count($order);$i++)
{
	$temp=$order[$i];
	$item= $temp->item;
	$price_per_item = $temp->price_per_item;
	$quantity = $temp->quantity;
//	echo "INSERT INTO `Order`(`item`, `quantity`, `price_per_item`, `idBid`) VALUES (\"$item\",$quantity,$price_per_item,$idBid)";
	mysqli_query($con,"INSERT INTO `Order`(`item`, `quantity`, `price_per_item`, `idBid`) VALUES (\"$item\",$quantity,$price_per_item,$idBid)") or die("Error213 : ".mysqli_error($con));
}

//echo $idBid;


	$output=[];
	$arr = array(
		    "Tag" => "Update", 
		);
	$output[]=$arr;

/*
 * Following code adds a new user broadcast to the database
 */
 
// array for JSON response
$response = array();
 
// check for required fields
//if (  isset($_GET['user']) && isset($_GET['message'])) 
//{
 
$message="New bid added to your auction";
//echo "SELECT `idUser` from `Auction` where `idAuction`=$idAuction";
	$addquery = mysqli_query($con,"SELECT `idUser` from `Auction` where `idAuction`=$idAuction");
    
    
 
    // check if row inserted or not
//    if ($addquery) {

$row = mysqli_fetch_array($addquery);
$userId=$row['idUser'];
//$userId=$_GET['user'];
//echo $userId;
   
 
    // include db connect class
    require_once __DIR__ . '/../db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    
    // mysql inserting a new row
//echo "SELECT `emailId` from User where `idUser`=$userId";
    
	$addquery = mysql_query("SELECT `emailId` from User where `idUser`=$userId");
    
    
 
    // check if row inserted or not
    if ($addquery) {

$row = mysql_fetch_array($addquery);
$email=$row['emailId'];
//echo $email;


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
    //        echo $response;    


  //      include 'push.php';
//echo json_encode($response);
    } 

//	print(json_encode($arr));
	print(json_encode($output));

?>

