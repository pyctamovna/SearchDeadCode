// Refactoring utilities - reserved for future auto-fix features
#![allow(dead_code)]
#![allow(unused_imports)]

mod safe_delete;
mod undo;
mod editor;

pub use safe_delete::SafeDeleter;
pub use undo::UndoScript;
pub use editor::FileEditor;
