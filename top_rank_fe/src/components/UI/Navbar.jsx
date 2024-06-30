import React from 'react';
import classes from './Navbar.module.css'

const Navbar = () => {
    return (
        <div className={classes.navbar}>
            <div>
                <h1>TOP RANK</h1>
            </div>
            <div className={classes.rightLinks}>
                <h2>Home</h2>
                <h2>Ranks</h2>
                <h2>Chat</h2>
                <h2>Contact</h2>
                <h2>Settings</h2>
                <h2>Log out</h2>
            </div>
        </div>
    );
};

export default Navbar;