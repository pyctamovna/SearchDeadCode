// Detectors for specific types of dead code
// These can be extended for more advanced analysis
#![allow(dead_code)]
#![allow(unused_imports)]

mod unused_class;
mod unused_method;
mod unused_property;
mod unused_import;
mod unused_param;
mod unused_enum_case;
mod assign_only;
mod dead_branch;
mod redundant_public;
mod write_only;
mod sealed_variant;
mod redundant_override;
mod ignored_return;
mod unused_intent_extra;

// These detectors are reserved for future advanced analysis modes
pub use unused_class::UnusedClassDetector;
pub use unused_method::UnusedMethodDetector;
pub use unused_property::UnusedPropertyDetector;
pub use unused_import::UnusedImportDetector;
pub use unused_param::UnusedParamDetector;
pub use unused_enum_case::UnusedEnumCaseDetector;
pub use assign_only::AssignOnlyDetector;
pub use dead_branch::DeadBranchDetector;
pub use redundant_public::RedundantPublicDetector;
pub use write_only::WriteOnlyDetector;
pub use sealed_variant::UnusedSealedVariantDetector;
pub use redundant_override::RedundantOverrideDetector;
pub use ignored_return::IgnoredReturnValueDetector;
pub use unused_intent_extra::{UnusedIntentExtraDetector, IntentExtraAnalysis, ExtraLocation};

use crate::analysis::DeadCode;
use crate::graph::Graph;

/// Trait for dead code detectors
pub trait Detector {
    /// Run the detector on the graph and return found issues
    fn detect(&self, graph: &Graph) -> Vec<DeadCode>;
}
