import {  useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";


function Login() {

    const [id, setId] = useState("");
    const navigate = useNavigate();


    async function login(event) {
        event.preventDefault();
        try {
          await axios.post("http://localhost:8085/login", {
            id: id
            }).then((res) =>
            {
             console.log(res.data);

             if (res.data.message == "ID does not exits")
             {
               alert("Id does not exits");
             }
             else if(res.data.message == "Login Success")
             {

                navigate('/home');
             }
              else
             {
                alert("Incorrect Id");
             }
          }, fail => {
           console.error(fail); // Error!
  });
        }


         catch (err) {
          alert(err);
        }

      }

    return (
       <div>
            <div class="container">
            <div class="row">
                <h2>Login</h2>
             <hr/>
             </div>

             <div class="row">
             <div class="col-sm-6">

            <form>
        <div class="form-group">
          <label>Email</label>
          <input  class="form-control" id="id" placeholder="Enter ID"

          value={id}
          onChange={(event) => {
            setId(event.target.value);
          }}

          />

        </div>
{/*         React.createElement(MyButton, {color: 'blue', shadowSize: 2}, 'Login') */}
//                   <button type="submit" class="btn btn-primary" onClick={login} >Login</button>
              </form>

            </div>
            </div>
            </div>

     </div>
    );
  }

  export default Login;