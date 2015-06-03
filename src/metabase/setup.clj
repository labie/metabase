(ns metabase.setup
  (:require [metabase.db :as db]
            [metabase.models.user :refer [User]]))

(defonce ^:private setup-token
  (atom nil))

(defn token-value
  "Return the value of the setup token, if any."
  []
  @setup-token)

(defn incomplete?
  "Return `true` if a setup token exists and no `Users` exist in the DB."
  []
  (and @setup-token
       (not (db/exists? User))))

(defn token-match?
  "Function for checking if the supplied string matches our setup token.
   Returns boolean `true` if supplied token matches `@setup-token`, `false` otherwise."
  [token]
  {:pre [(string? token)]}
  (= token @setup-token))

(defn token-create
  "Create and set a new `@setup-token`.
   Returns the newly created token."
  []
  (reset! setup-token (.toString (java.util.UUID/randomUUID))))

(defn token-clear
  "Clear the `@setup-token` if it exists and reset it to nil."
  []
  (reset! setup-token nil))
