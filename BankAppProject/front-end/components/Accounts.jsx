import { useEffect, useState, useMemo } from "react";
import { useLocation } from "react-router-dom";
import {FormGroup, FormControl, InputLabel, Input, Button, FormHelperText } from '@mui/material';

export default function Accounts() {
    const location = useLocation();
    const props = location.state;
    const inputsDefault = {accountName: "", openingBalance: 0};
    const [inputs, setInputs] = useState(inputsDefault);
    const [accounts, setAccounts] = useState([]);


useEffect(() => {
        // Clear the accounts array before fetching new data
        setAccounts([]);

        fetch(`http://localhost:8080/customer/${props.id}`)
            .then(res => res.json())
            .then(data => {
                // Use Promise.all to fetch all accounts in parallel
                return Promise.all(
                    data.accounts.map(account =>
                        fetch(`http://localhost:8080/account/${account}`)
                            .then(res => res.json())
                    )
                );
            })
            .then(fetchedAccounts => {
                setAccounts(fetchedAccounts); // Set all accounts at once
            })
            .catch(err => window.alert(err));
    }, [props.id]);

    const handlesubmit = async (event) => {
        event.preventDefault();
        await fetch(`http://localhost:8080/account`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                "customerId": props.id,
                "accountName": inputs["accountName"],
                "openingBalance": inputs["openingBalance"]
            })
        })
            .then(res => res.json())
            .then(newAccount => setAccounts([...accounts, newAccount]))
            .catch(err => window.alert(err));
        setInputs(inputsDefault);
    };

    const handleChange = (event) => {
        const { name, value } = event.target;
        setInputs(values => ({ ...values, [name]: value }));
    };

    const handleDelete = (event) => {
        event.preventDefault();
            const accountNumber = event.target.value;
            fetch(`http://localhost:8080/account/${accountNumber}`, {
                method: 'DELETE',
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                }
            })
            .then(res => res.json())
            .then(data => {
                window.alert(`Account closed, cash: $${data}`);
                setAccounts(accounts => accounts.filter(account => account.number !== Number(accountNumber)));
            })
            .catch(err => window.alert(err));
    };


    return (
        <div>
            <h1>Welcome {props.fullName} </h1>
            <h3>Here are your accounts: </h3>
            <ul>
                {accounts.map(account =>
                <li key={account.number}>
                    {account.name} - {account.openingBalance}
                    <button name="delete" value={account.number} onClick={handleDelete}>Delete</button>
                </li>)}
            </ul>
            <form onSubmit={handlesubmit}>
                <FormGroup sx={{display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between'}}>
                    <FormControl>
                        <InputLabel htmlFor="accountName">Account Name</InputLabel>
                        <Input id="accountName" name="accountName" aria-describedby="account name help" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <InputLabel htmlFor="openingBalance">Opening Balance</InputLabel>
                        <Input id="openingBalance" name="openingBalance" aria-describedby="opening balance help" type="number" onChange={handleChange} />
                    </FormControl>
                    <FormControl>
                        <Button type="submit">Submit</Button>
                    </FormControl>
                </FormGroup>
            </form>
        </div>
    );
}