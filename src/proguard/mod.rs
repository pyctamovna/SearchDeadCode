// ProGuard/R8 integration module
//
// Parses ProGuard/R8 output files to enhance dead code detection:
// - usage.txt: Lists code that ProGuard determined is unused
// - seeds.txt: Lists code that matched -keep rules
// - mapping.txt: Obfuscation mapping (for reverse lookups)

mod usage;
mod report_generator;

pub use usage::{ProguardUsage, UsageEntryKind};
pub use report_generator::ReportGenerator;
