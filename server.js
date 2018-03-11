var express=require('express')
var app=express();
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://adminwormvijaicv:Krish%401997@bookworm-shard-00-00-b3joy.mongodb.net:27017,bookworm-shard-00-01-b3joy.mongodb.net:27017,bookworm-shard-00-02-b3joy.mongodb.net:27017/test?ssl=true&replicaSet=bookworm-shard-0&authSource=admin";
var db;


MongoClient.connect(url, function(err,client) {
    if (err) throw err;
    console.log("Database ready!");
    db=client.db("ycmuserdata");
    
  });

app.get("/",function(req,res){
    res.send ("sever Online and listening....");
    
})

// app.get("/login",function(req,res){
    
// })


app.get("/login",function(req,res){ 
    var email=req.query.mail;
    db.collection("userinfo").find({email:email}).toArray((err,resp)=>{
        if(err)console.log(err);
        console.log(resp);
        res.json({stat:true});
        if(resp.length==0){
            db.collection("userinfo").insertOne({email:email},function(err,resp){
                if(err){
                    console.log(err);
                    res.json({stat:false,cause:"server error"});
                }
                else{
                    res.json({stat:true});
                }
                console.log(resp);
                
            });
        }
    })

})

app.get("/usertype",function(req,res){
    var email=req.query.mail;
    var type=req.query.type;

    db.collection("userinfo").update({email:email},{$set:{type:type}},{upsert:true},function(err,resp){
        if(err){
            console.log(err);
            res.json({stat:false,message:"server error"});
        }
        else{
            res.json({stat:true,message:"done"});
        }
    });
})

app.get("/nametag",function(req,res){
    var email=req.query.mail;
    var stringoftags=req.query.str;
    var name=req.query.name;
    console.log(stringoftags);
    var tags=stringoftags.split(",");
    var taga=[];
    for(var i=0;i<tags.length;i++){
      taga.push(tags[i]);
    }

    console.log(taga);
 
    db.collection("userinfo").update({email:email},{$set:{tags:taga,name:name}},{upsert:true},function(err,resp){
        if(err){
            console.log(err);
            res.json({stat:false,cause:"server error"});
        }
        console.log(resp);
        res.json({stat:true});
    });

})

app.get("/getcommnames",function(req,res){
    var email=req.query.mail;
    db.collection("userinfo").find({email:email}).toArray(function(err,resp){
        var tags=resp[0].tags;
        var arr=[];
        var resparr=[];
        for(var i=0;i<tags.length;i++){
            arr.push({tag:tags[i]});
        }
        db.collection("communities").find({$or:arr}).project({_id:false,name:true}).toArray((err,resp)=>{
            if(err)console.log(err);
            console.log(resp);
            for(var i=0;i<resp.length;i++){
                resparr.push({name:resp[i].name});
            }
            
            db.collection("communities").find().toArray((err,resp)=>{
                console.log(resp);
                for(var i=0;i<resp.length;i++){
                    resparr.push({name:resp[i].name});

                }
                res.json({communities:resparr});
            })

            
        })
    })


})


app.get("/followcommunity",function(req,res){
    var email=req.query.mail;
    var community=req.query.comname;
    var comarray=community.split(",");

    for(var i=0;i<comarray.length;i++){
    db.collection("userinfo").update({email:email},{$push:{communities:community}},function(err,resp){
        if(err){
            console.log(err);
            res.json({stat:false});
        }
    })

    db.collection("communities").update({name:community},{$inc:{followers:1}},function(err,resp){
        if(err){
            console.log(err);
            res.json({stat:false});
        }
    })
    }

    res.json({stat:true});

})


app.get("/comdetails",function(req,res){
    var comname=req.query.comname;
    db.collection("communities").find({name:comname}).toArray(function(err,resp){
        res.json({name:resp[0].name,desc:resp[0].description,followers:resp[0].followers});
    })
})

app.get("/feed",function(req,res){
    var comname=req.query.comname;
    db.collection("articles").find({by:comname}).project({_id:false}).toArray(function(err,resp){
        res.json({feed:resp});
    })
})

app.get("/addquestion",function(req,res){
    var question=req.query.question;
    var comname=req.query.comname;
    db.collection("articles").insertOne({title:question,by:comname},function(err,resp){
        res.json({stat:true});
    })
        
})


app.get("/adddoc",function(req,res){
    var email=req.query.email;
    var name=req.query.name;
    var yoe=req.query.yoe;
    var specarea=req.query.sa;
    var workingat=req.query.wa;
    var prn=req.query.prn;
    var cs=req.query.cs;
    var cul=req.query.link;

    var doc={
        email:email,
        name:name,
        specarea:specarea,
        YOE:yoe,
        workingat:workingat,
        prn:prn,
        certificate:cs,
        cul:cul    }

        db.collection.insert(doc,function(err,resp){
            if(err){
                console.log(err);
                res.json({stat:false,message:"server error"});
            }
            else{
                res.json({stat:true,message:"Doctor added"});
            }
        })
})

app.get("/logindoc",function(req,res){
    var email=req.query.mail;
    db.collection("Doctors").find({email:email}).toArray((err,resp)=>{
        console.log(resp);
        if(resp.length==0){
            db.collection("userinfo").insertOne({email:email},function(err,resp){
                if(err){
                    console.log(err);
                    res.json({stat:false,cause:"server error"});
                }
                else{
                    res.json({stat:true});
                }
                console.log(resp);
            });
        }
    })
})

app.get("/community",)



app.listen(3000,function(err){
    console.log("app listening at port :3000");
})