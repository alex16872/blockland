(ns blockland.main
  (:require [blockland.basicdemo :as basicdemo]
            [blockland.bullet :as bullet]
            [blockland.entities :as entities]
            [blockland.firstperson :as firstperson]
            [blockland.gameloop :as gameloop]
            [three :as three]))

(defonce game-state (atom {}))

(defn game-loop! [{:keys [delta-time keys-pressed]}]
  (let [{:keys [camera scene renderer] :as game} @game-state]
    (.render renderer scene camera)
    (bullet/bullet-system! game delta-time)))

(defn start-game! []
  (reset! game-state
          (firstperson/init-game)
          ;; (basicdemo/init-game)
          )
  (let [{:keys [renderer]} @game-state]
    (.appendChild (.-body js/document) (.-domElement renderer))
    (gameloop/run-game! (fn [data] (game-loop! data)))))

(defn init []
  (-> (js/Ammo)
      (.then start-game!)))

(defn reset-game! []
  (let [{:keys [renderer]} @game-state]
    (.remove (.-domElement renderer)))
  (reset! game-state
          (firstperson/init-game)
          ;; (basicdemo/init-game)
          )
  (let [{:keys [renderer]} @game-state]
    (.appendChild (.-body js/document) (.-domElement renderer))))


(comment

  (reset-game!)

  )
