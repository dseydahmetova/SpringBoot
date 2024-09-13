import {  useState } from "react";
import axios from "axios";
import * as React from 'react';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';


function Register() {
    const [fullName, setFullName] = useState("");

const handleChange = event => {
    setFullName(event.target.value);
    console.log('value is:', event.target.value);
    console.log(fullName);
    let data = {fullName:fullName};
    console.log(JSON.stringify(data));

};

        async function save(event) {
                  event.preventDefault();
                  try {
                      let data = {fullname: fullName};
                      const response = await axios.post("http://localhost:8080/customer",
                       fullName.toString(),
                       {
                           headers: {
                               "Content-Type":"text/plain"
                           }
                       }
                      );
                      console.log(response.data);
                      alert(`User registration complete. Login with customer ID: ${response.data.id}`);
                  } catch (err) {
                      alert(err);
                  }
              }

       return (
           <div>
               <div className="container mt-4">
                   <div className="card">
                       <h1>Customer Registration</h1>
                       <form>
                           <div className="form-group">
                               <TextField
                                   required
                                   id="outlined-required"
                                   label="Full Name"
                                   value={fullName}
                                   onChange={handleChange}
                               />
                           </div>
                           <Button type="submit" variant="contained" onClick={save}>
                               Register
                           </Button>
                       </form>
                   </div>
               </div>
           </div>
       );
   }

   export default Register;