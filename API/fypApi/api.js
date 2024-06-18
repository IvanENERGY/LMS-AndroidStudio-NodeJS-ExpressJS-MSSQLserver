const dboperation = require('./dboperation');
var Db = require('./dboperation');
//var Order = require('./order');

var express= require('express');
var bodyParser=require('body-parser');
var cors=require('cors');
const Account = require('./account');
var app=express();
var router = express.Router();


app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());
app.use(cors());
app.use('/api',router);


router.use((request,response,next)=>{

        //console.log('middleware');
        next();
})

app.get('/', (req, res) => {
        res.send('Express hosting on azure')
})

router.route('/login/:ac/:pw').get((request,response)=>{
        account=new Account(request.params.ac,request.params.pw)
        dboperation.login(account).then(result =>{
           

        response.json(result[0]);
        })

})

router.route('/borrow/:ac/:barcode').put((request,response)=>{
        
        dboperation.borrow(request.params.ac,request.params.barcode).then(result =>{
           

        response.json(result[0]);
        })

})
router.route('/ret/:ac/:barcode').put((request,response)=>{
        
        dboperation.returnBook(request.params.ac,request.params.barcode).then(result =>{
           

        response.json(result[0]);
        })

})
// router.route('/bookcopiesstatus/:barcode').get((request,response)=>{
        
//         dboperation.getBookCopiesStatus(request.params.barcode).then(result =>{
           
//         response.json(result[0]);
//         })

// })

router.route('/borrowedbooks/:ac').get((request,response)=>{
        
        dboperation.getAllBorrowedBooks(request.params.ac).then(result =>{
           
        response.json(result);
        })

})

router.route('/renew/:barcodeID').put((request,response)=>{
        
        dboperation.renew(request.params.barcodeID).then(result =>{
           
        response.json(result[0]);
        })

})



router.route('/books').get((request,response)=>{
        
        dboperation.getAllBook().then(result =>{
           
        response.json(result);
        })

})
router.route('/libs').get((request,response)=>{
        
        dboperation.getAllLib().then(result =>{
           
        response.json(result);
        })

})
router.route('/bookcopies/isbn/:isbn').get((request,response)=>{
        dboperation.getAllBookCopies(request.params.isbn).then(result =>{
           
                response.json(result);
        })

})


router.route('/acs/:ac').get((request,response)=>{
        dboperation.getAccountInfo(request.params.ac).then(result =>{
           
                response.json(result[0]);
        })

})
router.route('/readers/:id').get((request,response)=>{
        dboperation.getReaderInfo(request.params.id).then(result =>{
           
                response.json(result[0]);
        })

})


router.route('/processret/:barcode').put((request,response)=>{
        dboperation.processRetReq(request.params.barcode).then(result =>{
           
                response.json(result[0]);
        })

})
router.route('/book').post((request,response)=>{
        let book = {... request.body}
        dboperation.addBook(book).then(result =>{
       
            response.json(result);
        })
})
router.route('/bookcopies').post((request,response)=>{
        let bookCop = {... request.body}
        dboperation.addBookCopies(bookCop).then(result =>{
       
            response.json(result);
        })
})
router.route('/bookcopies/:barcode').get((request,response)=>{
        dboperation.getBookCopiesInfo(request.params.barcode).then(result =>{
           
                response.json(result);
        })

})
router.route('/book').put((request,response)=>{
        let reqBody = {... request.body}
        dboperation.updateBook(reqBody).then(result =>{
       
            response.json(result);
        })
})
router.route('/bookcopies/:barcode/:ac').delete((request,response)=>{
       
        dboperation.delBook(request.params.barcode,request.params.ac).then(result =>{
       
            response.json(result);
        })
})

var port = process.env.PORT||3001;
app.listen(port);
console.log('FYP Api is running at'+port);

