import {  useState } from "react";
import axios from "axios";
import * as React from 'react';
import {useNavigate} from 'react-router-dom';
import Button from '@mui/material/Button';
import {FormGroup, FormControl, InputLabel, Input, FormHelperText } from '@mui/material';
import TextField from '@mui/material/TextField';
import { Padding } from "@mui/icons-material";


function Home() {
    const navigate = useNavigate();
    const [fullName, setFullName] = useState("");

const handleChange = event => {
    setFullName(event.target.value);
    console.log('value is:', event.target.value);
    let data = {fullName:fullName};

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
                      alert(`User registration complete. Login with customer ID: ${response.data.id}`);
                  } catch (err) {
                      alert(err);
                  }
              }

       return (
           <div style={{marginTop:'10vh'}} >
            <form onSubmit={save}>
                <FormGroup sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'space-between'}}>
                    <FormControl sx={{margin: '3rem'}}>
                        <InputLabel htmlFor="outlined-required">Full Name</InputLabel>
                        <Input id="outlined-required" name="full name" aria-describedby="account name help" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <Button type="submit">Register</Button>
                    </FormControl>
                </FormGroup>
            </form>

           </div>
       );
   }

   export default Home;