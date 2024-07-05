import React, { useState } from "react";
import { Sidebar } from "primereact/sidebar";
import classes from "./scss/burgermenu.module.css";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { ProfileImage } from "..";

export default function BurgerMenu() {
  const [visible, setVisible] = useState<boolean>(false);
  const user = useSelector((state: any) => state.auth.user);
  return (
    <>
      <Sidebar
        className={classes.burger_menu}
        visible={visible}
        onHide={() => setVisible(false)}
        header={
          <div className={classes.burger_header}>
            {user.photoUrl !== null ? (
              <img src={user.photoUrl} alt="profile_photo" />
            ) : (
              <ProfileImage
                photoUrl={user.photoUrl}
                username={user.username}
                style={{
                  backgroundColor: user.accountColor,
                  width: "42px",
                  height: "42px",
                  fontSize: "18px",
                  marginRight: "10px",
                }}
                canClick={false}
              />
            )}
            <span>{user.username}</span>
          </div>
        }
        closeOnEscape={true}
      >
        <ul className={classes.burger_list}>
          <Link to="/">
            <li>
              <img src="/Assets/SVG/HomeIcon.svg" alt="Home" />
              <span>Home</span>
            </li>
          </Link>
          <Link to={`/profile/${user.username}`}>
            <li>
              <img src="/Assets/SVG/ProfileIcon.svg" alt="Home" />
              <span>Profile</span>
            </li>
          </Link>
          <Link to="/play">
            <li>
              <img src="/Assets/SVG/BattleIcon.svg" alt="Home" />
              <span>Play</span>
            </li>
          </Link>
          <Link to="/clan">
            <li>
              <img src="/Assets/SVG/shieldIcon.svg" alt="Home" />
              <span>Clan</span>
            </li>
          </Link>
          <Link to="/leaderboard">
            <li>
              <img src="/Assets/SVG/leaderboard.svg" alt="Home" />
              <span>Leaderboard</span>
            </li>
          </Link>
        </ul>
        <div className={classes.divider}></div>
        <div className={classes.logout_btn}>
          <img src="/Assets/SVG/exit.svg" alt="Exit" />
          <span>Logout</span>
        </div>
      </Sidebar>
      <div className={classes.burger_btn} onClick={() => setVisible(true)}>
        <i className="bi bi-list" />
      </div>
    </>
  );
}
