import { FormControl, InputLabel, Input, Button, FormHelperText } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function Login() {
    const navigate = useNavigate();
    const  handleSubmit =  async (event) => {
        event.preventDefault();
        const customerNumber = event.target[0].value;
        await fetch(`http://localhost:8080/customer/${customerNumber}`)
            .then(res =>{
                if(res.ok){
                    return res.json();
                } else {
                    throw new Error('Something went wrong');
                }
            }).then(data =>{
                window.alert('Logged in successfully.')
                navigate('/accounts', {state: data});
            })
            .catch(err => window.alert(err));
    }
    return (
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
            <h1 style={{margin: 'auto', padding: '3rem'}}>Login</h1>
            <form onSubmit={handleSubmit} >
                <FormControl sx={{display: 'flex', flexDirection: 'row', alignItems: 'center'}} >
                    <InputLabel htmlFor="customer-number">Customer Number</InputLabel>
                    <Input id="customer-number" aria-describedby="customer number help" type="number" />
                    
                </FormControl>
            </form>
        </div>
    );
}